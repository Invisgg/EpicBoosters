package gg.minecrush.boosters.Listeners;

import gg.minecrush.boosters.database.json.values.ValuesClass;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.util.Objects;

public class ExperienceListener implements Listener {

    ValuesClass values;

    public ExperienceListener(ValuesClass values){
        this.values = values;
    }

    @EventHandler
    public void onBlockExp(BlockExpEvent e) {
        String activeBooster = values.getValueString("activeBooster");
        if (Objects.equals(activeBooster, "experience")) {
            int originalExp = e.getExpToDrop();
            int multiplier = values.getValueInt("multiplier");
            e.setExpToDrop(multiplier * originalExp);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        String activeBooster = values.getValueString("activeBooster");
        if (Objects.equals(activeBooster, "experience")) {
            LivingEntity entity = (LivingEntity) e.getEntity();
            if (entity instanceof Player) {
                return;
            } else {
                int originalExp = e.getDroppedExp();
                int multiplier = values.getValueInt("multiplier");
                e.setDroppedExp(multiplier * originalExp);
            }
        }
    }
}
