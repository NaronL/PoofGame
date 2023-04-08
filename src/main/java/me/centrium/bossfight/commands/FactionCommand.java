package me.centrium.bossfight.commands;

import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.menu.FactionMenu;
import me.centrium.bossfight.user.User;
import me.centrium.bossfight.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.commands.SpigotCommand;
import ru.luvas.rmcs.utils.Util;
import sexy.kostya.mineos.perms.PermissionGroup;

public class FactionCommand extends SpigotCommand {
    public FactionCommand(){
        super("faction", PermissionGroup.PLAYER, "/faction");
        this.unavailableFromConsole();
    }

    @Override
    public void handle(CommandSender commandSender, String label, String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        User user = BossFight.getInstance().getUserManager().getUser(player);

        if(!user.getFaction().getId().equalsIgnoreCase("none")){
            ChatUtils.sendMessage(player, Util.wrapRed("Вы уже состоите во фракции!"));
            return;
        }

        if(user.getLevel() < 5){
            ChatUtils.sendMessage(player, Util.wrapRed("Вам необходимо достигнуть 5-го уровня!"));
            return;
        }

        new FactionMenu(player);
    }
}
