package me.centrium.bossfight.commands;

import me.centrium.bossfight.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.luvas.rmcs.commands.SpigotCommand;
import ru.luvas.rmcs.utils.Util;
import sexy.kostya.mineos.perms.PermissionGroup;

public class GiftCommand extends SpigotCommand {
    public GiftCommand(){
        super("gift", PermissionGroup.PLAYER, "/gift");
        this.unavailableFromConsole();
    }
    @Override
    public void handle(CommandSender commandSender, String label, String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        switch (args.length){
            case 1:
                try{
                    Player target = Bukkit.getPlayer(args[0]);
                    ItemStack itemStack = player.getItemInHand();

                    if(itemStack.getType() == Material.AIR){
                        ChatUtils.sendMessage(player, Util.wrapRed("Вам нужно взять предмет в руку!"));
                        return;
                    }

                    if(!target.getLocation().getWorld().equals(player.getLocation().getWorld()) && player.getLocation().distanceSquared(target.getLocation()) <= 25){
                        ChatUtils.sendMessage(player, Util.wrapRed("Игрок должен находиться в пяти блоках от вас!"));
                        return;
                    }

                    if(target == player){
                        ChatUtils.sendMessage(player, Util.wrapRed("Вы не можете отправлять предмет самому себе!"));
                        return;
                    }

                    ChatUtils.sendMessage(player, Util.wrapGreen("Вы передали предмет игроку " + target.getName()));
                    player.getInventory().remove(itemStack);
                    ChatUtils.sendMessage(target, Util.wrapGreen("Вы получили предмет от игрока " + player.getName()));
                    player.getInventory().addItem(itemStack);
                } catch (NullPointerException exception){
                    ChatUtils.sendMessage(player, Util.wrapRed("Данный игрок не в сети!"));
                    return;
                }
                break;
            default:
                ChatUtils.sendMessage(player, Util.wrapRed("/gift [Игрок]"));
                break;
        }
    }
}
