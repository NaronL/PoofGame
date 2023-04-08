package me.centrium.bossfight.listener;

import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.blocksprice.BlocksPrice;
import me.centrium.bossfight.user.User;
import me.centrium.bossfight.user.UserSettings;
import me.centrium.bossfight.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import ru.luvas.rmcs.player.RPlayer;
import ru.luvas.rmcs.utils.Util;

import java.util.Collection;

public class GlobalListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        User user = BossFight.getInstance().getUserManager().getUser(player);
        Collection<ItemStack> drops = event.getBlock().getDrops();

        if(player.getGameMode() != GameMode.SURVIVAL){
            event.setCancelled(true);
            return;
        }

        drops.forEach(action -> {
            if(BossFight.getInstance().getBlocksPriceManager().getBlocksPrice(action.getType()) != null){
                BlocksPrice blocksPriceInfo = BossFight.getInstance().getBlocksPriceManager().getBlocksPrice(action.getType());

                event.getBlock().setType(Material.AIR);
                event.getBlock().getDrops().clear();
                player.getInventory().addItem(blocksPriceInfo.getIcon());
                user.addTotalblocks(1);
                user.addBlockCount(blocksPriceInfo, 1);
            } else {
                event.setCancelled(true);
            }
        });
    }

    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        User user = BossFight.getInstance().getUserManager().getUser(player);
        RPlayer rPlayer = RPlayer.get(player);

        event.setCancelled(true);

        if(event.getMessage().startsWith("@")){
            if(!user.getFaction().getId().equalsIgnoreCase("none")){
                if(user.getSettings(UserSettings.FACTION_CHAT) == 0){
                    ChatUtils.sendMessage(player, Util.wrapRed("У вас выключен чат фракции!"));
                    return;
                }
                Bukkit.getOnlinePlayers().stream()
                        .map(users -> BossFight.getInstance().getUserManager().getUser(users))
                        .filter(users -> users.getFaction().getId().equalsIgnoreCase(user.getFaction().getId()) && users.getSettings(UserSettings.FACTION_CHAT) == 1)
                        .forEach(players -> {
                            ChatUtils.sendMessage(player, "&7[&aФракция&7] %s &7[&6%s ур.&7]: &f%s", rPlayer.getColoredName(), user.getLevel(), event.getMessage().replaceFirst("@", ""));
                            return;
                        });
            } else {
                ChatUtils.sendMessage(player, Util.wrapRed("Вы не являетесь членом фракции!"));
                return;
            }
            return;
        } else {
            Bukkit.getOnlinePlayers().forEach(players -> {
                ChatUtils.sendMessage(players, "%s &7[&6%s ур.&7]%s: &f%s", rPlayer.getColoredName(), user.getLevel(), user.getFaction().getId().equalsIgnoreCase("none") ? "" : " &7[" + user.getFaction().getPrefix() + "&7]", event.getMessage());
            });
            return;
        }
    }
}
