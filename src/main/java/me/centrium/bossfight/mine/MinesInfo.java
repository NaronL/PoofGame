package me.centrium.bossfight.mine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.centrium.bossfight.user.User;
import me.centrium.bossfight.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.luvas.rmcs.utils.Util;

@Getter
@AllArgsConstructor
public class MinesInfo {
    private String id;
    private String name;
    private String lore;
    private int level;
    private Material material;
    private Location location;
    private int slot;

    public ItemStack getIcon(User user) {
        ItemBuilder itemBuilder = ItemBuilder.newBuilder(material);
        if (user.getLevel() >= this.level) {
            itemBuilder.name(this.name);
            if(this.lore != null){
                itemBuilder.lore(lore.split("\\r?\\n"));
            }
        } else {
            itemBuilder.material(Material.RED_CONCRETE_POWDER);
            itemBuilder.name(this.name);
            itemBuilder.lore(
                    "",
                    "&fВам необходимо достигнуть " + Util.wrapRed(String.valueOf(this.level)) + " &fуровня!",
                    "",
                    Util.wrapRed("Недоступно"));
        }
        return itemBuilder.build();
    }
}
