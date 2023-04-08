package me.centrium.bossfight.commands;

import me.centrium.bossfight.menu.MineMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.commands.SpigotCommand;
import sexy.kostya.mineos.perms.PermissionGroup;

public class MineCommand extends SpigotCommand {
    public MineCommand(){
        super("mine", PermissionGroup.PLAYER, "/mine");
        this.unavailableFromConsole();
    }

    @Override
    public void handle(CommandSender commandSender, String label, String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());

        new MineMenu(player);
    }
}
