package me.centrium.bossfight.menu;

import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.utils.ItemBuilder;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.api.RMCSAPI;
import ru.luvas.rmcs.api.inventory.DynamicInventory;
import ru.luvas.rmcs.api.inventory.DynamicItem;
import ru.luvas.rmcs.utils.Util;

import java.util.concurrent.atomic.AtomicInteger;

public class ItemsMenu {
    DynamicInventory dynamicInventory;

    public ItemsMenu(Player player){
        dynamicInventory = RMCSAPI.getInventoryAPI().createNewInventory(Util.wrapGold("Предметы"), 6);

        AtomicInteger slotIndex = new AtomicInteger();
        BossFight.getInstance().getItemManager().getCache().forEach((key, value) -> {
            dynamicInventory.addItem(slotIndex.getAndIncrement(), new DynamicItem(
                    ItemBuilder.newBuilder(value.getUsableItem())
                            .build(), (toPlayer, clickType, slot) -> {
                        player.getInventory().addItem(value.getUsableItem());
            }));
        });

        dynamicInventory.open(player);
    }
}
