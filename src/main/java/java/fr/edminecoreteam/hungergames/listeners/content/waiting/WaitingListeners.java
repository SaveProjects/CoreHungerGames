package fr.edminecoreteam.hungergames.listeners.content.waiting;

import fr.edminecoreteam.hungergames.Core;
import fr.edminecoreteam.hungergames.State;
import fr.edminecoreteam.hungergames.utils.game.GameUtils;
import fr.edminecoreteam.hungergames.content.waiting.items.ItemsWaiting;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WaitingListeners implements Listener
{
    private Player p;
    private static final Core core = Core.getInstance();

    public WaitingListeners(Player p)
    {
        this.p = p;
    }
    private final GameUtils gameUtils = new GameUtils();

    public WaitingListeners()
    {
        //null
    }

    public void getWaitingItems()
    {
        ItemsWaiting waiting = new ItemsWaiting(p);
        waiting.clearinv();
        waiting.get();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e)
    {
        if (e.getItem() == null) { return; }

        Player p = e.getPlayer();
        Action a = e.getAction();
        ItemStack it = e.getItem();
        if (it.getType() == Material.BED && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK))
        {
            e.setCancelled(true);
            p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
        }
    }

    @EventHandler
    private void onDrop(PlayerDropItemEvent e)
    {
        if (core.isState(State.WAITING) || core.isState(State.STARTING) || core.isState(State.FINISH))
        {
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void onDamage(EntityDamageEvent e)
    {
        if (e.getEntity().getType() == EntityType.PLAYER)
        {
            if (core.isState(State.WAITING) || core.isState(State.STARTING) || core.isState(State.FINISH))
            {
                e.setCancelled(true);
                if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID))
                {
                    Player p = (Player) e.getEntity();
                    p.teleport(gameUtils.getSpawn());
                }
            }
        }
    }

    @EventHandler
    private void onHungerBarChange(FoodLevelChangeEvent e)
    {
        if (e.getEntityType() != EntityType.PLAYER)
        {
            return;
        }
        if (core.isState(State.WAITING) || core.isState(State.STARTING) || core.isState(State.FINISH))
        {
            e.setCancelled(true);
        }
    }
}
