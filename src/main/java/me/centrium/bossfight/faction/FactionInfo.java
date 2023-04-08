package me.centrium.bossfight.faction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
public class FactionInfo {
    private String id;
    private String name;
    private String color;
    private String prefix;
    private Material material;
    private Location location;
    private int slot;
}
