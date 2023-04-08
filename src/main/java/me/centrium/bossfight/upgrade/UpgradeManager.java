package me.centrium.bossfight.upgrade;

import lombok.Getter;
import lombok.SneakyThrows;
import me.centrium.bossfight.BossFight;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class UpgradeManager {

    @Getter
    private final Map<String, UpgradeInfo> cache = new HashMap<>();

    @SneakyThrows
    public UpgradeManager(){
        ResultSet resultSet = BossFight.getInstance().getDatabaseConnector().getConnector().query("SELECT * FROM bossfight_upgrade");
        while (resultSet.next()){
            String id = resultSet.getString("id");
            String item = resultSet.getString("item");
            int slot = resultSet.getInt("slot");

            cache.put(id, new UpgradeInfo(id, item, slot));
        }
    }
}
