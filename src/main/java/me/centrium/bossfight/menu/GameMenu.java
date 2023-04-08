package me.centrium.bossfight.menu;

import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.user.User;
import me.centrium.bossfight.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.api.RMCSAPI;
import ru.luvas.rmcs.api.inventory.DynamicInventory;
import ru.luvas.rmcs.api.inventory.DynamicItem;
import ru.luvas.rmcs.utils.Util;

public class GameMenu {
    DynamicInventory dynamicInventory;

    public GameMenu(Player player){
        dynamicInventory = RMCSAPI.getInventoryAPI().createNewInventory(Util.wrapGold("Меню"), 6);
        User user = BossFight.getInstance().getUserManager().getUser(player);

        for(int i = 0; i <= dynamicInventory.getSize(); i++){
            if(dynamicInventory.getItem(i) == null){
                dynamicInventory.addItem(i, dynamicInventory.addItem(i, new DynamicItem(ItemBuilder.newBuilder(Material.BLACK_STAINED_GLASS_PANE).build(), ((toPlayer, clickType, slot) -> {}))));
            }
        }

        dynamicInventory.addItem(13, new DynamicItem(ItemBuilder.newBuilder(Material.NETHER_STAR).name(Util.wrapGold("Донат")).build(), (o, e, s) -> {}));

        dynamicInventory.addItem(19, new DynamicItem(ItemBuilder.newBuilder(Material.LEGACY_TOTEM).name(Util.wrapGold("Улучшение персонажа")).build(), (o, e, s) -> {}));

        dynamicInventory.addItem(21, new DynamicItem(ItemBuilder.newBuilder(Material.APPLE).name(Util.wrapGold("Магазин")).build(), (o, e, s) -> new ShopMenu(player)));

        dynamicInventory.addItem(22, new DynamicItem(ItemBuilder.newBuilder(Material.GOLD_BLOCK).name(Util.wrapGold("Продать блоки")).build(), (o, e, s) -> {
            user.sellBlocks();
            player.closeInventory();
        }));

        dynamicInventory.addItem(23, new DynamicItem(ItemBuilder.newBuilder(Material.COMPARATOR).name(Util.wrapGold("Настройки")).build(), (o, e, s) -> new SettingsMenu(player)));

        dynamicInventory.addItem(25, new DynamicItem(ItemBuilder.newBuilder(Material.LEGACY_EXP_BOTTLE).name(Util.wrapGold("Повышение уровня")).build(), (o, e, s) -> new LevelMenu(player)));

        dynamicInventory.addItem(29, new DynamicItem(ItemBuilder.newBuilder(Material.GRASS_BLOCK).name(Util.wrapGold("Спавн")).build(), (o, e, s) -> {}));

        dynamicInventory.addItem(31, new DynamicItem(ItemBuilder.newBuilder(Material.PAPER).name(Util.wrapGold("Стоимость блоков")).build(), (o, e, s) -> new BlocksInformationMenu(player)));

        dynamicInventory.addItem(33, new DynamicItem(ItemBuilder.newBuilder(Material.IRON_PICKAXE).name(Util.wrapGold("Локации")).build(), (o, e, s) -> new MineMenu(player)));

        dynamicInventory.addItem(37, new DynamicItem(ItemBuilder.newBuilder(Material.DIAMOND_BLOCK).name(Util.wrapGold("Список активных бустеров")).build(), (o, e, s) -> {}));

        dynamicInventory.addItem(40, new DynamicItem(ItemBuilder.newBuilder(Material.WOODEN_AXE).name(Util.wrapGold("Улучшение предметов")).build(), (o, e, s) -> new UpgradeMenu(player)));

        dynamicInventory.addItem(43, new DynamicItem(ItemBuilder.newBuilder(Material.BOOK).name(Util.wrapGold("Сломанные блоки")).build(), (o, e, s) -> new BrokenBlocksMenu(player)));

        dynamicInventory.open(player);
    }
}
