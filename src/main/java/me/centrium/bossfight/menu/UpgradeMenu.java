package me.centrium.bossfight.menu;

import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.item.ItemInfo;
import me.centrium.bossfight.utils.ItemBuilder;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.api.RMCSAPI;
import ru.luvas.rmcs.api.inventory.DynamicInventory;
import ru.luvas.rmcs.api.inventory.DynamicItem;
import ru.luvas.rmcs.utils.Util;

public class UpgradeMenu {

    DynamicInventory dynamicInventory;

    public UpgradeMenu(Player player){
        dynamicInventory = RMCSAPI.getInventoryAPI().createNewInventory(Util.wrapGold("Улучшение предметов"), 6);

        BossFight.getInstance().getUpgradeManager().getCache().forEach((key, value) -> {
            ItemInfo itemInfo = ItemInfo.getItem(key);

            dynamicInventory.addItem(value.getSlot(), new DynamicItem(ItemBuilder.newBuilder(itemInfo.getUpgradeItem()).build(), ((toPlayer, clickType, slot) -> {

            })));
        });

        dynamicInventory.open(player);
    }
}
