package me.centrium.bossfight.faction;

import lombok.Getter;
import lombok.SneakyThrows;
import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import ru.luvas.rmcs.utils.Util;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FactionManager {

    @Getter
    private final Map<String, FactionInfo> cache = new HashMap<>();

    @SneakyThrows
    public FactionManager(){
        ResultSet resultSet = BossFight.getInstance().getDatabaseConnector().getConnector().query("SELECT * FROM bossfight_faction");
        while(resultSet.next()){
            String id = resultSet.getString("id");
            String name = Util.f(resultSet.getString("name"));
            String color = Util.f(resultSet.getString("color"));
            String prefix = Util.f(resultSet.getString("prefix"));
            Material material = Material.valueOf(resultSet.getString("material"));
            Location location = LocationUtils.fromString(resultSet.getString("location"));
            int slot = resultSet.getInt("slot");

            cache.put(id, new FactionInfo(id, name, color, prefix, material, location, slot));
        }
    }

    public FactionInfo getFaction(String id){
        return cache.values().stream()
                .filter(factionInfo -> factionInfo.getId().equalsIgnoreCase("id"))
                .findAny()
                .get();
    }

    public int getValueMember(String id){
        return Math.toIntExact(Bukkit.getOnlinePlayers().stream()
                .map(players -> BossFight.getInstance().getUserManager().getUser(players))
                .filter(faction -> faction.getFaction().getId().equalsIgnoreCase(id))
                .count());
    }
}
