package me.centrium.bossfight.randomevent;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.SneakyThrows;
import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.luvas.rmcs.utils.Task;
import ru.luvas.rmcs.utils.Util;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomEventManager {

    private static final @Getter Map<String, RandomEventInfo> cache = new HashMap<>();

    @SneakyThrows
    public RandomEventManager(){
        ResultSet resultSet = BossFight.getInstance().getDatabaseConnector().getConnector().query("SELECT * FROM bossfight_events");
        while (resultSet.next()){
            String id = resultSet.getString("id");
            String name = Util.f(resultSet.getString("name"));
            PotionEffectType potionEffectType = PotionEffectType.getByName(resultSet.getString("effectType"));
            int delay = resultSet.getInt("delay");
            int amplifier = resultSet.getInt("amplifier");

            cache.put(id, new RandomEventInfo(id, name, potionEffectType, delay, amplifier));
        }
    }

    public RandomEventInfo getId(String id){
        return cache.values().stream()
                .filter(randomEventInfo -> randomEventInfo.getId()
                .equalsIgnoreCase(id))
                .findAny()
                .get();
    }

    public void start(){
        Task.schedule(() -> {
            RandomEventInfo randomEventInfo = BossFight.getInstance().getRandomEventManager().getId(String.valueOf(ThreadLocalRandom.current().nextInt(1, cache.size())));

            if(randomEventInfo == null) return;

            if(Bukkit.getOnlinePlayers().isEmpty()) return;

            Bukkit.getOnlinePlayers().forEach(players -> {
                ChatUtils.sendMessage(players, Lists.newArrayList(Util.wrapGold("Случайное событие"), randomEventInfo.getName()));
                players.addPotionEffect(new PotionEffect(randomEventInfo.getPotionEffectType(), randomEventInfo.getDelay(), randomEventInfo.getAmplifier()));
            });
        }, 12000L, 12000L);
    }
}
