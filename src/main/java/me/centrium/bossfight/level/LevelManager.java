package me.centrium.bossfight.level;

import com.google.common.reflect.TypeToken;
import lombok.Getter;
import lombok.SneakyThrows;
import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.blocksprice.BlocksPrice;
import me.centrium.bossfight.user.User;
import me.centrium.bossfight.utils.JsonUtils;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class LevelManager {

    @Getter
    private final Map<Integer, LevelInfo> cache = new HashMap<>();
    private final Type blocks_type = (new TypeToken<ConcurrentMap<String, Integer>>() {
    }).getType();

    @SneakyThrows
    public LevelManager(){
        ResultSet resultSet = BossFight.getInstance().getDatabaseConnector().getConnector().query("SELECT * FROM bossfight_level");
        while (resultSet.next()){
            int level = resultSet.getInt("level");
            double exp = resultSet.getDouble("exp");

            cache.put(level, new LevelInfo(exp, JsonUtils.GSON.fromJson(resultSet.getString("blocks"), this.blocks_type)));
        }
    }

    public boolean hasNeed(Player player){
        User user = BossFight.getInstance().getUserManager().getUser(player);
        LevelInfo levelInfo = BossFight.getInstance().getLevelManager().getNextLevel(player);
        AtomicBoolean access = new AtomicBoolean(false);
        levelInfo.getBlocks().forEach((levelKey, levelValue) -> cache.forEach((key, value) -> {
            BlocksPrice blocksPrice = BossFight.getInstance().getBlocksPriceManager().getId(levelKey);
            if(user.getExp() >= value.getExp() && user.getBlockCount(blocksPrice) >= levelValue){
                access.set(true);
            }
        }));
        return access.get();
    }

    public LevelInfo getNextLevel(Player player){
        return BossFight.getInstance().getLevelManager().getCache().get(BossFight.getInstance().getUserManager().getUser(player).getLevel() + 1);
    }

    public int getMaxLevel(){
        return Collections.max(cache.keySet());
    }
}
