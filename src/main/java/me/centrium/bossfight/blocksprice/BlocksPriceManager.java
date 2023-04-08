package me.centrium.bossfight.blocksprice;

import lombok.Getter;
import lombok.SneakyThrows;
import me.centrium.bossfight.BossFight;
import org.bukkit.Material;
import ru.luvas.rmcs.utils.Util;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class BlocksPriceManager {

    @Getter
    private final Map<String, BlocksPrice> cache = new HashMap<>();

    @SneakyThrows
    public BlocksPriceManager(){
        ResultSet resultSet = BossFight.getInstance().getDatabaseConnector().getConnector().query("SELECT * FROM bossfight_blocks");
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = Util.f(resultSet.getString("name"));
            double price = resultSet.getDouble("price");
            Material material = Material.valueOf(resultSet.getString("material"));
            int level = resultSet.getInt("level");

            cache.put(id, new BlocksPrice(id, name, material, price, level));
        }
    }

    public BlocksPrice getBlocksPrice(Material material){
        for(BlocksPrice blocksPrice : BossFight.getInstance().getBlocksPriceManager().getCache().values()){
            if(blocksPrice.getMaterial() == material){
                return blocksPrice;
            }
        }
        return null;
    }

    public BlocksPrice getId(String id){
        return BossFight.getInstance().getBlocksPriceManager().getCache().get(id);
    }
}
