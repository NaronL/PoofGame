package me.centrium.bossfight.menu;

import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.user.User;
import me.centrium.bossfight.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.api.RMCSAPI;
import ru.luvas.rmcs.api.inventory.DynamicInventory;
import ru.luvas.rmcs.utils.Util;

import java.util.Comparator;

public class BlocksInformationMenu {

    DynamicInventory dynamicInventory;

    public BlocksInformationMenu(Player player) {
        dynamicInventory = RMCSAPI.getInventoryAPI().createNewInventory(Util.wrapGold("Стоимость блоков"), 6);
        User user = BossFight.getInstance().getUserManager().getUser(player);

        BossFight.getInstance().getBlocksPriceManager().getCache().entrySet()
                .stream()
                .sorted(Comparator.comparingInt(sorted -> Integer.parseInt(sorted.getKey())))
                .forEach(blocksInfo -> {
                    dynamicInventory.getBukkitInventory().addItem(user.getLevel() >= blocksInfo.getValue().getLevel()
                            ? ItemBuilder.newBuilder(blocksInfo.getValue().getMaterial())
                            .name(blocksInfo.getValue().getName())
                            .lore(
                                    "&fЦена за блок: " + Util.wrapGold(String.valueOf(blocksInfo.getValue().getPrice())),
                                    "&fЦена за стак: " + Util.wrapGold(String.valueOf(blocksInfo.getValue().getPrice() * 64)))
                            .build()
                            : ItemBuilder.newBuilder(Material.RED_CONCRETE_POWDER)
                            .name(Util.wrapRed("Данный блок вам недоступен!"))
                            .lore("&fНеобходим " + Util.wrapRed(String.valueOf(blocksInfo.getValue().getLevel())) + " уровень")
                            .build());
                });

        dynamicInventory.open(player);
    }
}
