package me.centrium.bossfight.menu;

import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.user.User;
import me.centrium.bossfight.utils.ChatUtils;
import me.centrium.bossfight.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.api.RMCSAPI;
import ru.luvas.rmcs.api.inventory.DynamicInventory;
import ru.luvas.rmcs.api.inventory.DynamicItem;
import ru.luvas.rmcs.utils.Util;

public class MineMenu {

     DynamicInventory dynamicInventory;

    public MineMenu(Player player){
        dynamicInventory = RMCSAPI.getInventoryAPI().createNewInventory(Util.wrapGold("Список локаций"), 5);
        User user = BossFight.getInstance().getUserManager().getUser(player);

        for(int i = 0; i <= dynamicInventory.getSize(); i++){
            if(dynamicInventory.getItem(i) == null){
                dynamicInventory.addItem(i, dynamicInventory.addItem(i, new DynamicItem(ItemBuilder.newBuilder(Material.BLACK_STAINED_GLASS_PANE).build(), ((toPlayer, clickType, slot) -> {}))));
            }
        }

        BossFight.getInstance().getMinesManager().getCache().forEach((key, value) -> {
            dynamicInventory.addItem(value.getSlot(), new DynamicItem(value.getIcon(user), ((toPlayer, clickType, slot) -> {
                if(clickType.isLeftClick()){
                    if(user.getLevel() < value.getLevel()){
                        ChatUtils.sendMessage(player, Util.wrapRed("Вам необходимо достигнуть " + value.getLevel() + "-го уровня!"));
                        player.closeInventory();
                        return;
                    }
                    player.teleport(value.getLocation());
                }
            })));
        });
        dynamicInventory.open(player);
    }
}
