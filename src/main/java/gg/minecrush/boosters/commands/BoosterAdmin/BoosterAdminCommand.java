package gg.minecrush.boosters.commands.BoosterAdmin;

import gg.minecrush.boosters.Boosters;
import gg.minecrush.boosters.config.Config;
import gg.minecrush.boosters.config.MessagesConfig;
import gg.minecrush.boosters.database.json.values.ValuesClass;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BoosterAdminCommand implements CommandExecutor {

    private final Boosters plugin;
    private final ValuesClass values;
    private final MessagesConfig messagesConfig;
    private final Config config;

    public BoosterAdminCommand(Boosters plugin, ValuesClass value, MessagesConfig messagesConfig, Config config) {
        this.plugin = plugin;
        this.values = value;
        this.messagesConfig = messagesConfig;
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(messagesConfig.getMessages("admin-permission-node").replace("%p%", messagesConfig.getMessages("prefix")))) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("stop")) {
                    values.setHost("");
                    values.setActiveBooster("");
                    values.setMultiplier(1);
                    values.setGlobalActive(false);
                    sender.sendMessage(messagesConfig.getMessages("boosterStopped").replace("%p%", messagesConfig.getMessages("prefix")));
                } else if (args[0].equalsIgnoreCase("reload")) {
                    messagesConfig.reloadConfig();
                    config.reloadConfig();
                    sender.sendMessage(messagesConfig.getMessages("config-reload").replace("%p%", messagesConfig.getMessages("prefix")));
                }
            } else {
                sender.sendMessage(messagesConfig.getMessages("boosterAdminUsage").replace("%p%", messagesConfig.getMessages("prefix")));
            }
        } else {
            sender.sendMessage(messagesConfig.getMessages("insufficient-permissions").replace("%p%", messagesConfig.getMessages("prefix")));
        }
        return false;
    }


}
