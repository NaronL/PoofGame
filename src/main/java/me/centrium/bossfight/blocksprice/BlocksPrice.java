package me.centrium.bossfight.blocksprice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.centrium.bossfight.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public class BlocksPrice {
    private String id;
    private String name;
    private Material material;
    private double price;
    private int level;

    public ItemStack getIcon(){
        return ItemBuilder.newBuilder(material)
                .name(name)
                .build();
    }
}
