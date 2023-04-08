package me.centrium.bossfight.listener;

import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.user.User;
import me.centrium.bossfight.user.UserSettings;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLoadListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        User user = BossFight.getInstance().getUserManager().getUser(event.getPlayer());
        BossFight.getInstance().getUserManager().loadPlayer(event.getPlayer());

        event.setJoinMessage(null);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        BossFight.getInstance().getUserManager().unload(event.getPlayer());

        event.setQuitMessage(null);
    }
}
