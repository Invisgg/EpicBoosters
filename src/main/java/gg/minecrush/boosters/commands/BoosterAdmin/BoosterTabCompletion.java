package gg.minecrush.boosters.commands.BoosterAdmin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class BoosterTabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<String>();

        if (args.length == 1) {
            list.add("stop");
            list.add("reload");
            return list;
        }

        return null;
    }
}
