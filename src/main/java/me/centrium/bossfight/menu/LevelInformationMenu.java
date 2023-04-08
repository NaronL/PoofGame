package me.centrium.bossfight.menu;

import com.google.common.collect.Lists;
import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.blocksprice.BlocksPrice;
import me.centrium.bossfight.level.LevelInfo;
import me.centrium.bossfight.user.User;
import me.centrium.bossfight.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.api.RMCSAPI;
import ru.luvas.rmcs.api.inventory.DynamicInventory;
import ru.luvas.rmcs.utils.Util;

import java.util.List;

public class LevelInformationMenu {

    DynamicInventory dynamicInventory;

    public LevelInformationMenu(Player player){
        dynamicInventory = RMCSAPI.getInventoryAPI().createNewInventory(Util.wrapGold("Список уровней"), 3);
        User user = BossFight.getInstance().getUserManager().getUser(player);
        LevelInfo levelInfo = BossFight.getInstance().getLevelManager().getNextLevel(player);
        List<String> lore = Lists.newArrayList();

        ChatColor expColor = (user.getExp() >= levelInfo.getExp() ? ChatColor.GREEN : ChatColor.RED);
        lore.add("");
        lore.add(Util.wrapGold("Требования"));
        lore.add("&7▪ &fОпыта: " + expColor + user.getExp() + "/" + levelInfo.getExp());
        lore.add("");
        lore.add(Util.wrapGold("Сломать блоки"));
        levelInfo.getBlocks().forEach((key, value) -> {
            BlocksPrice blocksPrice = BossFight.getInstance().getBlocksPriceManager().getId(key);
            ChatColor blocksColor = (user.getBlockCount(blocksPrice) >= value ? ChatColor.GREEN : ChatColor.RED);
            lore.add("&7▪ " + blocksPrice.getName() + " &7» " + blocksColor + user.getBlockCount(blocksPrice) + "/" + value + "");
        });

        BossFight.getInstance().getLevelManager().getCache().forEach((key, value) -> {
            dynamicInventory.getBukkitInventory().addItem(ItemBuilder.newBuilder(Material.PAPER)
                    .name(Util.wrapGold("Уровень: " + key))
                    .lore(lore)
                    .build());
        });

        dynamicInventory.open(player);
    }
}
