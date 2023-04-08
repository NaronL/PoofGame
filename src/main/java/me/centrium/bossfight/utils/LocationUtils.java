package me.centrium.bossfight.utils;


import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@UtilityClass
public class LocationUtils {

    public Location fromString(String location) {
        String[] args = location.split(" ");
        if (args.length < 4) {
            throw new IllegalArgumentException("parameters == not");
        }
        World world = Bukkit.getWorld(args[0]);
        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);
        double z = Double.parseDouble(args[3]);

        return new Location(world, x, y, z);
    }

    public String toString(Location location){
        return location.getWorld().getName() + "" + location.getX() + ' ' + location.getY() + " " + location.getZ();
    }
}
