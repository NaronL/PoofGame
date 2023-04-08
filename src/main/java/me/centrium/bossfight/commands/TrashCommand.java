package me.centrium.bossfight.commands;

import me.centrium.bossfight.menu.TrashMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.commands.SpigotCommand;
import sexy.kostya.mineos.perms.PermissionGroup;

public class TrashCommand extends SpigotCommand {
    public TrashCommand(){
        super("trash", PermissionGroup.PLAYER, "/trash");
    }
    @Override
    public void handle(CommandSender commandSender, String s, String[] strings) {
        Player player = Bukkit.getPlayer(commandSender.getName());

        new TrashMenu(player);
    }
}
