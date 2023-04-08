package me.centrium.bossfight.item;

import com.google.common.reflect.TypeToken;
import lombok.Getter;
import lombok.SneakyThrows;
import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.utils.JsonUtils;
import org.bukkit.Material;
import ru.luvas.rmcs.utils.Util;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class ItemManager {

    @Getter
    private final Map<String, ItemInfo> cache = new HashMap<>();
    private final Type enchantmentType = (new TypeToken<ConcurrentMap<String, Integer>>() {
    }.getType());

    @SneakyThrows
    public ItemManager(){
        ResultSet resultSet = BossFight.getInstance().getDatabaseConnector().getConnector().query("SELECT * FROM bossfight_items");
        while (resultSet.next()){
            String id = resultSet.getString("id");
            String nextId = resultSet.getString("nextId");
            String name = Util.f(resultSet.getString("name"));
            String lore = Util.f(resultSet.getString("lore"));
            Material material = Material.valueOf(resultSet.getString("material"));
            int price = resultSet.getInt("price");
            int level = resultSet.getInt("level");

            cache.put(id, new ItemInfo(id, nextId, name, lore, material, price, level, JsonUtils.GSON.fromJson(resultSet.getString("enchantment"), enchantmentType)));
        }
    }
}
