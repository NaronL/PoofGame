package me.centrium.bossfight.commands;

import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.commands.SpigotCommand;
import sexy.kostya.mineos.perms.PermissionGroup;

public class SellBlocksCommand extends SpigotCommand {

    public SellBlocksCommand(){
        super("sellblocks", PermissionGroup.PLAYER, "/sellblocks");
    }

    @Override
    public void handle(CommandSender commandSender, String label, String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        User user = BossFight.getInstance().getUserManager().getUser(player);

        user.sellBlocks();
    }
}
