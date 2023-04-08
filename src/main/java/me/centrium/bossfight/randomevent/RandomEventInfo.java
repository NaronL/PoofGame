package me.centrium.bossfight.randomevent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.potion.PotionEffectType;

@Getter
@AllArgsConstructor
public class RandomEventInfo {
    private String id;
    private String name;
    private PotionEffectType potionEffectType;
    private int delay;
    private int amplifier;
}
