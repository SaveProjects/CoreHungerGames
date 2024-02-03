package fr.edminecoreteam.hungergames.end;

import fr.edminecoreteam.hungergames.Core;
import fr.edminecoreteam.hungergames.end.items.ItemsEnd;
import fr.edminecoreteam.hungergames.end.tasks.AutoStop;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class End
{

    private final static Core core = Core.getInstance();
    private final Location spawn = new Location(Bukkit.getWorld(
            core.getConfig().getString("spawn.world")),
            (float) core.getConfig().getDouble("spawn.x"),
            (float) core.getConfig().getDouble("spawn.y"),
            (float) core.getConfig().getDouble("spawn.z"),
            (float) core.getConfig().getDouble("spawn.f"),
            (float) core.getConfig().getDouble("spawn.t"));

    public void end()
    {
        for (Player pls : core.getServer().getOnlinePlayers())
        {
            pls.getEquipment().setHelmet(null);
            pls.getEquipment().setChestplate(null);
            pls.getEquipment().setLeggings(null);
            pls.getEquipment().setBoots(null);
            pls.getInventory().clear();
            pls.setHealth(20);
            pls.setFoodLevel(20);
            pls.teleport(spawn);
            pls.setGameMode(GameMode.ADVENTURE);
            ItemsEnd itemsEnd = new ItemsEnd(pls);
            itemsEnd.get();
        }

        AutoStop autoStop = new AutoStop(core);
        autoStop.runTaskTimer((Plugin) core, 0L, 20L);
    }

}
