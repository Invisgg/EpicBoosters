package gg.minecrush.boosters.Listeners;

import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import gg.minecrush.boosters.database.json.values.ValuesClass;

import java.util.List;
import java.util.Objects;

public class MobListener implements Listener {

    ValuesClass values;

    public MobListener(ValuesClass values){
        this.values = values;
    }

    @EventHandler
    public void mobDeath(EntityDeathEvent e) {
        String activeBooster = values.getValueString("activeBooster");
        if (Objects.equals(activeBooster, "drops")) {
            if (e.getEntity() instanceof Mob) {
                List<ItemStack> drops = e.getDrops();
                for (ItemStack drop : drops) {
                    int originalAmount = drop.getAmount();
                    int multi = values.getValueInt("multiplier");
                    drop.setAmount(originalAmount * multi);
                }
            }
        }
    }
}
