package gg.minecrush.boosters.commands.GrantBoosters;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class GrantTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<String>();

        if (args.length == 2){
            list.add("drops");
            list.add("experience");
            list.add("damage");
        }

        if (args.length == 3){
            list.add("<multiplier>");
        }

        if (args.length == 4){
            list.add("<time in seconds>");
        }

        if (args.length == 5){
            list.add("<amount>");
        }

        if (list.isEmpty()){
            return null;
        } else {
            return list;
        }
    }
}
