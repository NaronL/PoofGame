package me.centrium.bossfight.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.utils.Util;

import java.util.List;

@UtilityClass
public class ChatUtils {
    
    public void sendMessage(Player player, String message){
        if(player == null) return;

        player.sendMessage(Util.f(message));
    }

    public void sendMessage(Player player, String message, Object... objects){
        if(player == null) return;

        player.sendMessage(Util.f((String.format(message, objects))));
    }

    public void sendMessage(Player player, List<String> message){
        if(player == null) return;

        for(String text : message) player.sendMessage(Util.f((text)));
    }

    public void sendMessage(Player player, List<String> message, Object... objects){
        if(player == null) return;

        for(String text : message) player.sendMessage(Util.f(String.format(text, objects)));
    }

    public void sendTitle(Player player, String title, String subtitle){
        if(player == null) return;

        player.sendTitle(Util.f(title), Util.f(subtitle));
    }

    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut){
        if(player == null) return;

        player.sendTitle(Util.f(title), Util.f(subtitle), fadeIn, stay, fadeOut);
    }
}
