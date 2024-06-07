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

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BoosterStartEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final String boosterType;
    private final int multiplier;
    private final int duration;

    public BoosterStartEvent(Player player, String boosterType, int multiplier, int duration) {
        this.player = player;
        this.boosterType = boosterType;
        this.multiplier = multiplier;
        this.duration = duration;
    }

    public Player getPlayer() {
        return player;
    }

    public String getBoosterType() {
        return boosterType;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
