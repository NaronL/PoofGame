package me.centrium.bossfight.menu;

import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.item.ItemInfo;
import me.centrium.bossfight.user.User;
import me.centrium.bossfight.utils.ChatUtils;
import me.centrium.bossfight.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.luvas.rmcs.api.RMCSAPI;
import ru.luvas.rmcs.api.inventory.DynamicInventory;
import ru.luvas.rmcs.api.inventory.DynamicItem;
import ru.luvas.rmcs.utils.Util;

public class ShopMenu {

    DynamicInventory dynamicInventory;

    public ShopMenu(Player player){
        dynamicInventory = RMCSAPI.getInventoryAPI().createNewInventory(Util.wrapGold("Магазин"), 6);
        User user = BossFight.getInstance().getUserManager().getUser(player);


        BossFight.getInstance().getShopManager().getCache().forEach((key, value) -> {
            ItemInfo itemInfo = ItemInfo.getItem(value.getItem());

            dynamicInventory.addItem(value.getSlot(), new DynamicItem((user.getLevel() >= value.getLevel()
                    ? ItemBuilder.newBuilder(itemInfo.getUsableItem())
                    .addLore(
                            "",
                            Util.wrapGold("-------------"),
                            "&fЦена: " + Util.wrapGold(String.valueOf(value.getPrice())),
                            "&fУровень: " + Util.wrapGold(String.valueOf(value.getLevel())),
                            "&fКоличество: " + Util.wrapGold(String.valueOf(value.getAmount())),
                            Util.wrapGold("&7-------------"),
                            "",
                            Util.wrapGold("Нажмите ЛКМ, чтобы купить")
                    )
                    .build()
                    : ItemBuilder.newBuilder(Material.RED_STAINED_GLASS_PANE)
                    .name(itemInfo.getName())
                    .lore("", Util.wrapRed("Доступно только с " + value.getLevel() + " уровня!"))
                    .build()), ((toPlayer, clickType, slot) -> {
                if (clickType.isLeftClick()) {
                    if(user.getLevel() < value.getLevel()){
                        ChatUtils.sendMessage(player, Util.wrapRed("Вам необходим " + value.getLevel() + " уровень!"));
                        player.closeInventory();
                        return;
                    }
                    if(!user.hasBalance(value.getPrice())){
                        ChatUtils.sendMessage(player, Util.wrapRed("У вас недостаточно монет!"));
                        player.closeInventory();
                        return;
                    }

                    player.getInventory().addItem(ItemBuilder.newBuilder(itemInfo.getUsableItem()).amount(value.getAmount()).build());
                    user.takeBalance(value.getPrice());
                    ChatUtils.sendMessage(player, Util.wrapGold("Предмет куплен за " + value.getPrice() + " монет!"));
                    player.closeInventory();
                }
            })));
        });

        dynamicInventory.open(player);
    }
}
