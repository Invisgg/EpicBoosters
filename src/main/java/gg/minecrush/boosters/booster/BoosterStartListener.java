package gg.minecrush.boosters.booster;

//
//
// ,---._
//         ,----..                                            ,-.                                                                  .--.--.                                                              .-- -.' \   .--.--.
//         /   /   \                                       ,--/ /|                   ,---,           ,---,                         /  /    '.                                                            |    |   : /  /    '.
//         |   :     :   __  ,-.                          ,--. :/ |                 ,---.'|         ,---.'|                        |  :  /`. /        ,---,     ,---.            .---.                    :    ;   ||  :  /`. /
//         .   |  ;. / ,' ,'/ /|                          :  : ' /                  |   | :         |   | :                        ;  |  |--`     ,-+-. /  |   '   ,'\          /. ./|                    :        |;  |  |--`
//         .   ; /--`  '  | |' |    ,--.--.       ,---.   |  '  /       ,---.       |   | |         :   : :          .--,          |  :  ;_      ,--.'|'   |  /   /   |      .-'-. ' |       .--,         |    :   :|  :  ;_
//         ;   | ;     |  |   ,'   /       \     /     \  '  |  :      /     \    ,--.__| |         :     |,-.     /_ ./|           \  \    `.  |   |  ,"' | .   ; ,. :     /___/ \: |     /_ ./|         :          \  \    `.
//         |   : |     '  :  /    .--.  .-. |   /    / '  |  |   \    /    /  |  /   ,'   |         |   : '  |  , ' , ' :            `----.   \ |   | /  | | '   | |: :  .-'.. '   ' .  , ' , ' :         |    ;   |  `----.   \
//         .   | '___  |  | '      \__\/: . .  .    ' /   '  : |. \  .    ' / | .   '  /  |         |   |  / : /___/ \: |            __ \  \  | |   | |  | | '   | .; : /___/ \:     ' /___/ \: |     ___ l           __ \  \  |
//         '   ; : .'| ;  : |      ," .--.; |  '   ; :__  |  | ' \ \ '   ;   /| '   ; |:  |         '   : |: |  .  \  ' |           /  /`--'  / |   | |  |/  |   :    | .   \  ' .\     .  \  ' |   /    /\    J   : /  /`--'  /
//         '   | '/  : |  , ;     /  /  ,.  |  '   | '.'| '  : |--'  '   |  / | |   | '/  '         |   | '/ :   \  ;   :          '--'.     /  |   | |--'    \   \  /   \   \   ' \ |   \  ;   :  /  ../  `..-    ,'--'.     /
//         |   :    /   ---'     ;  :   .'   \ |   :    : ;  |,'     |   :    | |   :    :|         |   :    |    \  \  ;            `--'---'   |   |/         `----'     \   \  |--"     \  \  ;  \    \         ;   `--'---'
//         \   \ .'             |  ,     .-./  \   \  /  '--'        \   \  /   \   \  /           /    \  /      :  \  \                      '---'                      \   \ |         :  \  \  \    \      ,'
//         `---`                `--`---'       `----'                `----'     `----'            `-'----'        \  ' ;                                                  '---"           \  ' ;   "---....--'
//         `--`                                                                    `--`
//

import gg.minecrush.boosters.Bossbar.BossBarManager;
import gg.minecrush.boosters.config.MessagesConfig;
import gg.minecrush.boosters.database.json.values.ValuesClass;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import static gg.minecrush.boosters.util.TimeFormatter.formatTime;

public class BoosterStartListener implements Listener {

    private final BossBarManager bossBarManager;

    private final ValuesClass value;

    private final MessagesConfig messagesConfig;

    public BoosterStartListener(Plugin plugin, ValuesClass value, MessagesConfig messagesConfig) {
        this.bossBarManager = new BossBarManager(plugin, value, messagesConfig);
        this.value = value;
        this.messagesConfig = messagesConfig;
    }

    @EventHandler
    public void onBoosterStart(BoosterStartEvent event) {
        String message = messagesConfig.getMessages("bossbarMessage").replace("%player%",event.getPlayer().getName()).replace("%type%", capitalizeFirstLetter(event.getBoosterType())).replace("%multi%", "" + event.getMultiplier()).replace("%time%", formatTime(event.getDuration()));

        value.setGlobalActive(true);
        value.setMultiplier(event.getMultiplier());
        value.setHost(event.getPlayer().getName());
        value.setActiveBooster(event.getBoosterType());

        bossBarManager.createBossBar(message, BarColor.BLUE, BarStyle.SOLID, 1.0);
        bossBarManager.updateProgress(event.getDuration());

    }

    private static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
