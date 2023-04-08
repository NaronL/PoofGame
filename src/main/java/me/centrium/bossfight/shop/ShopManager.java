package me.centrium.bossfight.shop;

import lombok.Getter;
import lombok.SneakyThrows;
import me.centrium.bossfight.BossFight;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class ShopManager {

    @Getter
    private final Map<String, ShopInfo> cache = new HashMap<>();

    @SneakyThrows
    public ShopManager(){
        ResultSet resultSet = BossFight.getInstance().getDatabaseConnector().getConnector().query("SELECT * FROM bossfight_shop");
        while (resultSet.next()){
            String id = resultSet.getString("id");
            String item = resultSet.getString("item");
            int level = resultSet.getInt("level");
            int price = resultSet.getInt("price");
            int amount = resultSet.getInt("amount");
            int slot = resultSet.getInt("slot");

            cache.put(id, new ShopInfo(id, item, level, price, amount, slot));
        }
    }
}
