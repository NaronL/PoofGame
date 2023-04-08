package me.centrium.bossfight.menu;

import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.user.User;
import me.centrium.bossfight.user.UserSettings;
import me.centrium.bossfight.utils.ChatUtils;
import me.centrium.bossfight.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.api.RMCSAPI;
import ru.luvas.rmcs.api.inventory.DynamicInventory;
import ru.luvas.rmcs.api.inventory.DynamicItem;
import ru.luvas.rmcs.utils.Util;

public class SettingsMenu {

    DynamicInventory dynamicInventory;

    public SettingsMenu(Player player){
        dynamicInventory = RMCSAPI.getInventoryAPI().createNewInventory(Util.wrapGold("Настройки"), 3);
        User user = BossFight.getInstance().getUserManager().getUser(player);

        for(int i = 0; i <= dynamicInventory.getSize(); i++){
            if(dynamicInventory.getItem(i) == null){
                dynamicInventory.addItem(i, dynamicInventory.addItem(i, new DynamicItem(ItemBuilder.newBuilder(Material.BLACK_STAINED_GLASS_PANE).build(), ((toPlayer, clickType, slot) -> {}))));
            }
        }

        dynamicInventory.addItem(10, new DynamicItem(ItemBuilder.newBuilder(user.getSettings(UserSettings.HIDE_PLAYERS) == 1 ? Material.SPIDER_SPAWN_EGG : Material.CREEPER_SPAWN_EGG)
                .name("&fВидимость игроков: " + (user.getSettings(UserSettings.HIDE_PLAYERS) == 1 ? Util.wrapRed("отключено") : Util.wrapGreen("включено")))
                .lore("", user.getSettings(UserSettings.HIDE_PLAYERS) == 1 ? Util.wrapGreen("Нажмите, чтобы включить.") : Util.wrapRed("Нажмите, чтобы отключить."))
                .build(), ((toPlayer, clickType, slot) -> {
            if (user.getSettings(UserSettings.HIDE_PLAYERS) == 0) {
                user.setSettings(UserSettings.HIDE_PLAYERS, 1);
            } else {
                user.setSettings(UserSettings.HIDE_PLAYERS, 0);
            }
            ChatUtils.sendMessage(player, Util.wrapGreen("Настройки сохранены!"));
            player.closeInventory();
        })));

        dynamicInventory.addItem(12, new DynamicItem(ItemBuilder.newBuilder(user.getSettings(UserSettings.GET_MONEY) == 1 ? Material.SPIDER_SPAWN_EGG : Material.CREEPER_SPAWN_EGG)
                .name("&fПолучение денег: " + (user.getSettings(UserSettings.GET_MONEY) == 1 ? Util.wrapRed("отключено") : Util.wrapGreen("включено")))
                .lore("", user.getSettings(UserSettings.GET_MONEY) == 1 ? Util.wrapGreen("Нажмите, чтобы включить.") : Util.wrapRed("Нажмите, чтобы отключить."))
                .build(), ((toPlayer, clickType, slot) -> {
            if (user.getSettings(UserSettings.GET_MONEY) == 0) {
                user.setSettings(UserSettings.GET_MONEY, 1);
            } else {
                user.setSettings(UserSettings.GET_MONEY, 0);
            }
            ChatUtils.sendMessage(player, Util.wrapGreen("Настройки сохранены!"));
            player.closeInventory();
        })));

        dynamicInventory.addItem(14, new DynamicItem(ItemBuilder.newBuilder(user.getSettings(UserSettings.GET_ITEMS) == 1 ? Material.SPIDER_SPAWN_EGG : Material.CREEPER_SPAWN_EGG)
                .name("&fПолучение предметов &7» " + (user.getSettings(UserSettings.GET_ITEMS) == 1 ? Util.wrapRed("отключено") : Util.wrapGreen("включено")))
                .lore("", user.getSettings(UserSettings.GET_ITEMS) == 1 ? Util.wrapGreen("Нажмите, чтобы включить.") : Util.wrapRed("Нажмите, чтобы отключить."))
                .build(), ((toPlayer, clickType, slot) -> {
            if (user.getSettings(UserSettings.GET_ITEMS) == 0) {
                user.setSettings(UserSettings.GET_ITEMS, 1);
            } else {
                user.setSettings(UserSettings.GET_ITEMS, 0);
            }
            ChatUtils.sendMessage(player, Util.wrapGreen("Настройки сохранены!"));
            player.closeInventory();
        })));

        dynamicInventory.addItem(16, new DynamicItem(ItemBuilder.newBuilder(user.getSettings(UserSettings.FACTION_CHAT) == 1 ? Material.SPIDER_SPAWN_EGG : Material.CREEPER_SPAWN_EGG)
                .name("&fЧат фракции: " + (user.getSettings(UserSettings.FACTION_CHAT) == 1 ? Util.wrapRed("отключено") : Util.wrapGreen("включено")))
                .lore("", user.getSettings(UserSettings.FACTION_CHAT) == 1 ? Util.wrapGreen("Нажмите, чтобы включить.") : Util.wrapRed("Нажмите, чтобы отключить."))
                .build(), ((toPlayer, clickType, slot) -> {
            if (user.getSettings(UserSettings.FACTION_CHAT) == 0) {
                if(!user.getFaction().getId().equalsIgnoreCase("none")){
                    user.setSettings(UserSettings.FACTION_CHAT, 1);
                } else {
                    ChatUtils.sendMessage(player, Util.wrapRed("Вы не состоите во фракции!"));
                }
            } else {
                user.setSettings(UserSettings.FACTION_CHAT, 0);
            }
            ChatUtils.sendMessage(player, Util.wrapGreen("Настройки сохранены!"));
            player.closeInventory();
        })));

        dynamicInventory.open(player);
    }
}
