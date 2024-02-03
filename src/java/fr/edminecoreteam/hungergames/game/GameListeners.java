package fr.edminecoreteam.hungergames.game;

import fr.edminecoreteam.hungergames.Core;
import fr.edminecoreteam.hungergames.State;
import fr.edminecoreteam.hungergames.utils.game.GameUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;

public class GameListeners implements Listener
{
    private final static Core core = Core.getInstance();
    private final GameUtils gameUtils = new GameUtils();

    @EventHandler
    public void onMove(PlayerMoveEvent e)
    {
        if (core.isState(State.PREPARATION))
        {
            Player p = e.getPlayer();
            if (!core.getPlayersInGame().contains(p.getName()))
            {
                return;
            }

            Location playerLocation = core.spawnListeners().getPlayerLocation(p);
            Location pLoc = p.getLocation();
            if (playerLocation.getX() != pLoc.getX() || playerLocation.getY() != pLoc.getY() || playerLocation.getZ() != pLoc.getZ())
            {
                p.teleport(playerLocation);
            }
        }
    }

    @EventHandler
    public void noPvP(EntityDamageByEntityEvent e)
    {
        if (core.isState(State.NOPVP))
        {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e)
    {
        if (core.isState(State.NOPVP) || core.isState(State.INGAME))
        {
            if (e.getItemDrop().getItemStack().getType() == Material.COMPASS)
            {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e)
    {
        if (e.getCurrentItem() == null) { return; }
        if (core.isState(State.NOPVP) || core.isState(State.INGAME))
        {
            if (e.getCurrentItem().getType() == Material.COMPASS)
            {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onChestClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getHolder() instanceof Chest) {
            Chest chest = (Chest) inventory.getHolder();
            Block block = chest.getBlock();
            gameUtils.dropChestContents(block, inventory);
            block.setType(Material.AIR);
        }
    }
}
