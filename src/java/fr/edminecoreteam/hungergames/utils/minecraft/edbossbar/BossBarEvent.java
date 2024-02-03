package fr.edminecoreteam.hungergames.utils.minecraft.edbossbar;

import fr.edminecoreteam.hungergames.Core;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class BossBarEvent implements Listener
{

    private final static Core core = Core.getInstance();
    @EventHandler
    public void onPlayerWorldChange(PlayerChangedWorldEvent e)
    {
        Player p = e.getPlayer();
        core.getBossBar().removePlayer(p);
        core.getBossBar().putPlayer(p);
    }

    @EventHandler
    public void onWitherAttack(EntityDamageEvent e)
    {
        if (e.getEntityType() == EntityType.WITHER)
        {
            e.setCancelled(true);
        }
    }
}
