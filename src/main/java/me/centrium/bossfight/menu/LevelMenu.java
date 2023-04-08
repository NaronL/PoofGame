package me.centrium.bossfight.menu;

import com.google.common.collect.Lists;
import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.blocksprice.BlocksPrice;
import me.centrium.bossfight.level.LevelInfo;
import me.centrium.bossfight.user.User;
import me.centrium.bossfight.utils.ChatUtils;
import me.centrium.bossfight.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.api.RMCSAPI;
import ru.luvas.rmcs.api.inventory.DynamicInventory;
import ru.luvas.rmcs.api.inventory.DynamicItem;
import ru.luvas.rmcs.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class LevelMenu {

    DynamicInventory dynamicInventory;

    public LevelMenu(Player player){
        dynamicInventory = RMCSAPI.getInventoryAPI().createNewInventory(Util.wrapGold("Повышение уровня"), 3);

        User user = BossFight.getInstance().getUserManager().getUser(player);
        LevelInfo levelInfo = BossFight.getInstance().getLevelManager().getNextLevel(player);;

        List<String> lore = Lists.newArrayList();
        ChatColor expColor = (user.getExp() >= levelInfo.getExp() ? ChatColor.GREEN : ChatColor.RED);
        lore.add("&7▪ &fОпыта &7» " + expColor + user.getExp() + "/" + levelInfo.getExp());
        lore.add("");
        lore.add(Util.wrapGold("Сломать блоки"));
        levelInfo.getBlocks().forEach((key, value) -> {
            BlocksPrice blocksPrice = BossFight.getInstance().getBlocksPriceManager().getId(key);
            ChatColor blocksColor = (user.getBlockCount(blocksPrice) >= value ? ChatColor.GREEN : ChatColor.RED);
            lore.add("&7▪ " + blocksPrice.getName() + " &7» " + blocksColor + user.getBlockCount(blocksPrice) + "/" + value + "");
        });

        if(user.getLevel() >= BossFight.getInstance().getLevelManager().getMaxLevel()) {
            ChatUtils.sendMessage(player, Util.wrapRed("Вы достигли максимального уровня!"));
            return;
        }

        for(int i = 0; i <= dynamicInventory.getSize(); i++){
            if(dynamicInventory.getItem(i) == null){
                dynamicInventory.addItem(i, dynamicInventory.addItem(i, new DynamicItem(ItemBuilder.newBuilder(Material.BLACK_STAINED_GLASS_PANE).build(), ((toPlayer, clickType, slot) -> {}))));
            }
        }

        dynamicInventory.getBukkitInventory().setItem(11, ItemBuilder.newBuilder(Material.BOOK)
                .name(Util.wrapGold("Требования"))
                .lore(lore)
                .build());

        dynamicInventory.addItem(13, new DynamicItem(
                ItemBuilder.newBuilder(Material.LEGACY_EXP_BOTTLE)
                .name(Util.wrapGold("Повысить уровень"))
                .build(), (toPlayer, clickType, slot) -> {
            if (BossFight.getInstance().getLevelManager().hasNeed(player)) {
                user.levelUp();
                player.closeInventory();
            }
            ChatUtils.sendMessage(player, Util.wrapRed("Не все требования выполнены!"));
            player.closeInventory();
        }));

        dynamicInventory.addItem(15, new DynamicItem(ItemBuilder.newBuilder(Material.PAPER)
                .name(Util.wrapGold("Список уровней"))
                .build(), (toPlayer, clickType, slot) -> new LevelInformationMenu(player)));

        dynamicInventory.open(player);
    }
}
