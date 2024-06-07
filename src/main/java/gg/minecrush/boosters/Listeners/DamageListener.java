package gg.minecrush.boosters.Listeners;

import gg.minecrush.boosters.database.json.values.ValuesClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Objects;

public class DamageListener implements Listener {

    ValuesClass values;

    public DamageListener(ValuesClass values){
        this.values = values;
    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            String activeBooster = values.getValueString("activeBooster");
            if (Objects.equals(activeBooster, "damage")) {
                double damage = e.getDamage();
                int multi = values.getValueInt("multiplier");
                e.setDamage(damage * multi);
            }
        }
    }
}
