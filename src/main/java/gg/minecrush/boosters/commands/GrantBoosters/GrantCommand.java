package gg.minecrush.boosters.commands.GrantBoosters;

import gg.minecrush.boosters.config.MessagesConfig;
import gg.minecrush.boosters.database.json.playerdata.Json;
import gg.minecrush.boosters.util.color;
import gg.minecrush.boosters.util.TimeFormatter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;


public class GrantCommand implements CommandExecutor {

    private final Json json;
    private final MessagesConfig messagesConfig;

    public GrantCommand(Json json, MessagesConfig messagesConfig) {
        this.json = json;
        this.messagesConfig = messagesConfig;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(messagesConfig.getMessages("admin-permission-node").replace("%p%", messagesConfig.getMessages("prefix")))) {
            if (args.length >= 4) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    try {
                        String boosterType = color.capitalizeFirstLetter(args[1]);
                        int multiplier = Integer.parseInt(args[2]);
                        int time = Integer.parseInt(args[3]);
                        String formattedTime = TimeFormatter.formatTimeLonger(time);
                        int amount = (args.length >= 5) ? Integer.parseInt(args[4]) : 1;
                        json.addBooster(target, boosterType.toLowerCase(Locale.ROOT), amount, time , multiplier);
                        target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 2);
                        String bGiven = messagesConfig.getMessages("boosterGiven")
                                .replace("%player%", sender.getName())
                                .replace("%p%", messagesConfig.getMessages("prefix"))
                                .replace("%target%", target.getName())
                                .replace("%type%", boosterType)
                                .replace("%amount%", String.valueOf(amount))
                                .replace("%time%", formattedTime)
                                .replace("%multi%", String.valueOf(multiplier));

                        String bGranted = messagesConfig.getMessages("grantedBooster")
                                .replace("%player%", sender.getName())
                                .replace("%p%", messagesConfig.getMessages("prefix"))
                                .replace("%target%", target.getName())
                                .replace("%type%", boosterType)
                                .replace("%amount%", String.valueOf(amount))
                                .replace("%time%", formattedTime)
                                .replace("%multi%", String.valueOf(multiplier));

                        target.sendMessage(bGiven);
                        if (sender instanceof Player player) {
                            if (sender != target){
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 10, 5);
                                player.sendMessage(bGranted);
                            }
                        }
                    } catch (NumberFormatException e) {
                        sender.sendMessage(messagesConfig.getMessages("invalid-integer").replace("%p%", messagesConfig.getMessages("prefix")));
                    }
                } else {
                    sender.sendMessage(messagesConfig.getMessages("invalid-player").replace("%p%", messagesConfig.getMessages("prefix")));
                }
            } else {
                sender.sendMessage(messagesConfig.getMessages("grantBoosterUsage").replace("%p%", messagesConfig.getMessages("prefix")));
            }
        } else {
            sender.sendMessage(messagesConfig.getMessages("insufficient-permissions").replace("%p%", messagesConfig.getMessages("prefix")));
        }

        return false;
    }
}
