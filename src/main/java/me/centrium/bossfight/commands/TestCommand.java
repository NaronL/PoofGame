package me.centrium.bossfight.commands;

import me.centrium.bossfight.menu.GameMenu;
import ru.luvas.rmcs.api.RMCSAPI;
import ru.luvas.rmcs.api.player.RMCSPlayer;

public class TestCommand {

    public TestCommand(){
        RMCSAPI.getCommandsAPI().register("test", "/test", RMCSPlayer::isPlayer, ((rmcsPlayer, args) -> {
            new GameMenu(rmcsPlayer.getPlayer());

        }));
    }
}
