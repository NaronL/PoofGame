package me.centrium.bossfight.menu;

import org.bukkit.entity.Player;
import ru.luvas.rmcs.api.RMCSAPI;
import ru.luvas.rmcs.api.inventory.DynamicInventory;
import ru.luvas.rmcs.utils.Util;

public class TrashMenu {

    DynamicInventory dynamicInventory;

    public TrashMenu(Player player){
        dynamicInventory = RMCSAPI.getInventoryAPI().createNewInventory(Util.wrapGold("Мусорка"), 6);

        dynamicInventory.open(player);
    }
}
