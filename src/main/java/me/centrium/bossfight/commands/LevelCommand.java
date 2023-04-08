package me.centrium.bossfight.commands;

import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.menu.LevelMenu;
import me.centrium.bossfight.user.User;
import me.centrium.bossfight.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.commands.SpigotCommand;
import ru.luvas.rmcs.utils.Util;
import sexy.kostya.mineos.perms.PermissionGroup;

public class LevelCommand extends SpigotCommand {
    public LevelCommand(){
        super("level", PermissionGroup.PLAYER, "/level");
        this.unavailableFromConsole();;
    }

    @Override
    public void handle(CommandSender commandSender, String label, String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        User user = BossFight.getInstance().getUserManager().getUser(player);

        if(user.getLevel() >= BossFight.getInstance().getLevelManager().getMaxLevel()){
            ChatUtils.sendMessage(player, Util.wrapRed("Вы достигли максимального уровня!"));
            return;
        }

        new LevelMenu(player);
    }
}
