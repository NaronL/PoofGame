package me.centrium.bossfight.menu;

import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.faction.FactionInfo;
import me.centrium.bossfight.faction.FactionManager;
import me.centrium.bossfight.user.User;
import me.centrium.bossfight.utils.ChatUtils;
import me.centrium.bossfight.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.api.RMCSAPI;
import ru.luvas.rmcs.api.inventory.DynamicInventory;
import ru.luvas.rmcs.api.inventory.DynamicItem;
import ru.luvas.rmcs.utils.Util;

public class FactionMenu {
    DynamicInventory dynamicInventory;

    public FactionMenu(Player player){
        dynamicInventory = RMCSAPI.getInventoryAPI().createNewInventory(Util.wrapGold("Список фракций"), 3);
        User user = BossFight.getInstance().getUserManager().getUser(player);

        for(int i = 0; i <= dynamicInventory.getSize(); i++){
            if(dynamicInventory.getItem(i) == null){
                dynamicInventory.addItem(i, dynamicInventory.addItem(i, new DynamicItem(ItemBuilder.newBuilder(Material.BLACK_STAINED_GLASS_PANE).build(), ((toPlayer, clickType, slot) -> {}))));
            }
        }

        BossFight.getInstance().getFactionManager().getCache().forEach((key, value) -> {
            if(!key.equalsIgnoreCase("none")){
                dynamicInventory.addItem(value.getSlot(), new DynamicItem(ItemBuilder.newBuilder(value.getMaterial())
                        .name(value.getName())
                        .lore(
                                "",
                                "&fУчастников в сети: " + Util.wrapGold(String.valueOf(BossFight.getInstance().getFactionManager().getValueMember(key))),
                                "",
                                Util.wrapGold("Нажмите, чтобы вступить."))
                        .build(), ((toPlayer, clickType, slot) -> {
                    FactionInfo factionInfo = BossFight.getInstance().getFactionManager().getFaction(key);

                    user.setFaction(factionInfo);
                    ChatUtils.sendMessage(player, "Вы вступили во фракцию: " + factionInfo.getName());
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
                    player.closeInventory();
                })));
            }
        });

        dynamicInventory.open(player);
    }
}
