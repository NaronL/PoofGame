package me.centrium.bossfight.scoreboard;

import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import ru.luvas.rmcs.api.RMCSAPI;
import ru.luvas.rmcs.api.scoreboard.AnimationGamma;
import ru.luvas.rmcs.api.scoreboard.ScoreboardAPI;
import ru.luvas.rmcs.api.util.AutoListener;

import static me.centrium.bossfight.utils.NumberFormat.chars;

public class ScoreboardManager {

    private static final ScoreboardAPI scoreboardAPI = RMCSAPI.getScoreboardAPI();

    public static void load() {
        scoreboardAPI.prepare();
        scoreboardAPI.setDisplayName("BossFight", AnimationGamma.GOLD);
        new AutoListener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                setup(event.getPlayer());
                update(event.getPlayer());
            }

            @EventHandler
            public void onQuit(PlayerQuitEvent event) {
                scoreboardAPI.invalidate(event.getPlayer());
            }
        };
    }

    private static void setup(Player player) {
        User user = BossFight.getInstance().getUserManager().getUser(player);

        scoreboardAPI.addScore(player, 10, "&r&r");
        scoreboardAPI.addScore(player, 9, " &6Информация");
        scoreboardAPI.addScore(player, 8, " &fУровень: &e%s", user.getLevel());
        scoreboardAPI.addScore(player, 7, " &fМонет: &e%s", chars(user.getBalance()));
        scoreboardAPI.addScore(player, 6, " &fДобыто блоков: &e%s", chars(user.getTotalblocks()));
        scoreboardAPI.addScore(player, 5, " &fОпыта: &e%s", chars(user.getExp()));
        scoreboardAPI.addScore(player, 4, " &fРубинов: &e%s", chars(user.getRuby()));
        scoreboardAPI.addScore(player, 3, " &fУбийств: &e%s", user.getKills());
        scoreboardAPI.addScore(player, 2, " &fСмертей: &e%s", user.getDeaths());
        scoreboardAPI.addScore(player, 1, "&r");
        scoreboardAPI.addScore(player, 0, " &6rewforce.pw");
        player.setLevel(user.getLevel());
    }

    public static void update(Player player){
        User user = BossFight.getInstance().getUserManager().getUser(player);

        new BukkitRunnable(){
            @Override
            public void run() {
                scoreboardAPI.updateScore(player, 8, " &fУровень: &e%s", user.getLevel());
                scoreboardAPI.updateScore(player, 7, " &fМонет: &e%s", chars(user.getBalance()));
                scoreboardAPI.updateScore(player, 6, " &fДобыто блоков: &e%s", chars(user.getTotalblocks()));
                scoreboardAPI.updateScore(player, 5, " &fОпыта: &e%s", chars(user.getExp()));
                scoreboardAPI.updateScore(player, 4, " &fРубинов: &e%s", chars(user.getRuby()));
                scoreboardAPI.updateScore(player, 3, " &fУбийств: &e%s", user.getKills());
                scoreboardAPI.updateScore(player, 2, " &fСмертей: &e%s", user.getDeaths());

                player.setLevel(user.getLevel());
            }
        }.runTaskLaterAsynchronously(BossFight.getInstance(), 20L);
    }
}