package me.centrium.bossfight.mine;

import lombok.Getter;
import lombok.SneakyThrows;
import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.Material;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class MinesManager {

    @Getter
    private final Map<String, MinesInfo> cache = new HashMap<>();

    @SneakyThrows
    public MinesManager(){
        ResultSet resultSet = BossFight.getInstance().getDatabaseConnector().getConnector().query("SELECT * FROM bossfight_mines");
        while (resultSet.next()){
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            String lore = resultSet.getString("lore");
            int level = resultSet.getInt("level");
            Material material = Material.valueOf(resultSet.getString("material"));
            Location location = LocationUtils.fromString(resultSet.getString("location"));
            int slot = resultSet.getInt("slot");

            cache.put(id, new MinesInfo(id, name, lore, level, material, location, slot));
        }
    }

}
