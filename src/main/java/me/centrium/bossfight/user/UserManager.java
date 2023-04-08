package me.centrium.bossfight.user;

import com.google.common.reflect.TypeToken;
import lombok.Getter;
import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.utils.ChatUtils;
import me.centrium.bossfight.utils.JsonUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.luvas.rmcs.utils.Util;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
public class UserManager extends BukkitRunnable {
    private static final Map<String, User> cache = new HashMap<>();
    private final Type blocks_log = (new TypeToken<ConcurrentMap<String, Integer>>() {
    }).getType();
    private final Type settings = (new TypeToken<ConcurrentMap<UserSettings, Integer>>() {
    }).getType();

    public void loadPlayer(Player player){
        User.UserBuilder userBuilder = User.builder();
        User user;

        ConcurrentHashMap<String, Integer> blocks_log = new ConcurrentHashMap<>();
        BossFight.getInstance().getBlocksPriceManager().getCache().forEach((key, value) -> blocks_log.put(value.getId(), 0));

        ConcurrentHashMap<UserSettings, Integer> settings = new ConcurrentHashMap<>();
        Arrays.asList(UserSettings.values()).forEach(userSettings -> settings.put(userSettings, 1));

        try {
            ResultSet resultSet = BossFight.getInstance().getDatabaseConnector().getConnector().query("SELECT * FROM bossfight_users WHERE name='%s'", player.getName());
            if(resultSet.next()){
                user = userBuilder
                        .player(player)
                        .level(resultSet.getInt("level"))
                        .balance(resultSet.getDouble("balance"))
                        .totalblocks(resultSet.getDouble("totalblocks"))
                        .exp(resultSet.getDouble("exp"))
                        .ruby(resultSet.getInt("ruby"))
                        .kills(resultSet.getInt("kills"))
                        .deaths(resultSet.getInt("deaths"))
                        .faction(BossFight.getInstance().getFactionManager().getFaction(resultSet.getString("faction")))
                        .blocks_log(JsonUtils.GSON.fromJson(resultSet.getString("blocks_log"), this.blocks_log))
                        .settings(JsonUtils.GSON.fromJson(resultSet.getString("settings"), this.settings))
                        .build();

                ChatUtils.sendMessage(player, Util.wrapGreen("Ваши игровые данные загружены!"));
            } else {
                user = User.builder()
                        .player(player)
                        .level(1)
                        .balance(0)
                        .totalblocks(0.0D)
                        .exp(0.0D)
                        .ruby(0)
                        .kills(0)
                        .deaths(0)
                        .faction(null)
                        .blocks_log(blocks_log)
                        .settings(settings)
                        .build();

                this.insert(user);
            }
            cache.put(player.getName().toLowerCase(), user);
        } catch (SQLException e) {
            ChatUtils.sendMessage(player, Util.wrapRed("Произошла ошибка при загрузке ваших данных. Обратитесь к администрации!"));
            e.printStackTrace();
        }
    }

    public void insert(User user){
        BossFight.getInstance().getDatabaseConnector().getConnector().query("INSERT INTO bossfight_users (name, level, balance, totalblocks, exp, ruby, kills, deaths, faction, blocks_log, settings) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                user.getPlayer().getName(), user.getLevel(), user.getBalance(), user.getTotalblocks(), user.getExp(), user.getRuby(), user.getKills(), user.getDeaths(), user.getFaction() == null ? "none" : user.getFaction().getId(), JsonUtils.toJson(user.getBlocks_log()), JsonUtils.toJson(user.getSettings()));
    }

    public void save(User user){
        BossFight.getInstance().getDatabaseConnector().getConnector().query("UPDATE bossfight_users SET name = '%s', level = '%s', balance = '%s', totalblocks = '%s', exp = '%s', ruby = '%s', kills = '%s', deaths = '%s', faction = '%s', blocks_log = '%s', settings = '%s' WHERE name = '%s'",
                user.getPlayer().getName(), user.getLevel(), user.getBalance(), user.getTotalblocks(), user.getExp(), user.getRuby(), user.getKills(), user.getDeaths(), user.getFaction() == null ? "none" : user.getFaction().getId(), JsonUtils.toJson(user.getBlocks_log()), JsonUtils.toJson(user.getSettings()), user.getPlayer().getName());
    }

    public void unload(Player player){
        save(cache.remove(player.getName().toLowerCase()));
    }

    public User getUser(Player player) {
        return this.getUser(player.getName());
    }

    public User getUser(String name) {
        return cache.get(name.toLowerCase());
    }

    @Override
    public void run() {
        cache.values().forEach(this::save);
    }

}
