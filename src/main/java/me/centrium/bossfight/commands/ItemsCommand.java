package me.centrium.bossfight.commands;

import me.centrium.bossfight.menu.ItemsMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.commands.SpigotCommand;
import sexy.kostya.mineos.perms.PermissionGroup;

public class ItemsCommand extends SpigotCommand {
    public ItemsCommand(){
        super("items", PermissionGroup.PLAYER, "/items");
        this.unavailableFromConsole();
    }

    @Override
    public void handle(CommandSender commandSender, String label, String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());

        new ItemsMenu(player);
    }
}
