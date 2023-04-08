package me.centrium.bossfight.commands;

import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.user.User;
import me.centrium.bossfight.user.UserSettings;
import me.centrium.bossfight.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.luvas.rmcs.commands.SpigotCommand;
import ru.luvas.rmcs.utils.Util;
import sexy.kostya.mineos.perms.PermissionGroup;

public class SendMoneyCommand extends SpigotCommand {

    public SendMoneyCommand(){
        super("sendmoney", PermissionGroup.PLAYER, "/sendmoney [Игрок] [Сумма]");
    }

    @Override
    public void handle(CommandSender commandSender, String label, String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        User user = BossFight.getInstance().getUserManager().getUser(player);
        switch (args.length) {
            case 2:
                try {
                    Player targetPlayer = Bukkit.getPlayer(args[0]);
                    User userTarget = BossFight.getInstance().getUserManager().getUser(targetPlayer);
                    Double amount;

                    try {
                        amount = Double.valueOf(args[1].replace(",", ""));
                    } catch (NumberFormatException exception) {
                        ChatUtils.sendMessage(player, Util.wrapRed("Введите правильное число!"));
                        return;
                    }

                    if (amount < 1) {
                        ChatUtils.sendMessage(player, Util.wrapRed("Вы не можете отправить меньше 1 монеты!"));
                        return;
                    }

                    if (!user.hasBalance(amount)) {
                        ChatUtils.sendMessage(player, Util.wrapRed("У вас недостаточно монет для отправки!"));
                        return;
                    }

                    if(targetPlayer == player){
                        ChatUtils.sendMessage(player, Util.wrapRed("Вы не можете отправлять деньги самому себе!"));
                        return;
                    }

                    if(userTarget.getSettings(UserSettings.GET_MONEY) == 0){
                        ChatUtils.sendMessage(player, Util.wrapRed("У данного игрока отключено получение средств!"));
                        return;
                    }

                    user.takeBalance(amount);
                    userTarget.addBalance(amount);
                    ChatUtils.sendMessage(player, Util.wrapGreen("Вы отправили " + amount + " монет игроку " + targetPlayer.getName()));
                    ChatUtils.sendMessage(userTarget.getPlayer(),  Util.wrapGreen("Вы получили " + amount + " монет от игрока " + player.getName()));
                } catch (NullPointerException exception) {
                    ChatUtils.sendMessage(player, Util.wrapRed("Данный игрок находиться не в сети!"));
                    return;
                }
                break;
            default:
                ChatUtils.sendMessage(player, Util.wrapRed("/sendmoney [Игрок] [Сумма]"));
                break;
        }
    }
}
