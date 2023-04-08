package me.centrium.bossfight.menu;

import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.blocksprice.BlocksPrice;
import me.centrium.bossfight.user.User;
import me.centrium.bossfight.utils.ChatUtils;
import me.centrium.bossfight.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.api.RMCSAPI;
import ru.luvas.rmcs.api.inventory.DynamicInventory;
import ru.luvas.rmcs.utils.Util;

public class BrokenBlocksMenu {

    DynamicInventory dynamicInventory;

    public BrokenBlocksMenu(Player player){
        dynamicInventory = RMCSAPI.getInventoryAPI().createNewInventory(Util.wrapGold("Добытые блоки"), 6);
        User user = BossFight.getInstance().getUserManager().getUser(player);

        user.getBlocks_log().forEach((key, value) -> {
            BlocksPrice blocksPriceInfo = BossFight.getInstance().getBlocksPriceManager().getId(key);
            if(value > 0){
                dynamicInventory.getBukkitInventory().addItem(ItemBuilder.newBuilder(blocksPriceInfo.getMaterial())
                        .name(blocksPriceInfo.getName())
                        .lore("",
                                "&fДобыто блоков: " + Util.wrapGold(String.valueOf(value)),
                                "")
                        .build());
            }
        });

        dynamicInventory.open(player);
    }
}
