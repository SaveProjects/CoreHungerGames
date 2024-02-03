package fr.edminecoreteam.hungergames.utils.game;

import fr.edminecoreteam.hungergames.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GameUtils
{
    private static final Core core = Core.getInstance();
    private final Location spawn = new Location(Bukkit.getWorld(
            core.getConfig().getString("spawn.world")),
            (float) core.getConfig().getDouble("spawn.x"),
            (float) core.getConfig().getDouble("spawn.y"),
            (float) core.getConfig().getDouble("spawn.z"),
            (float) core.getConfig().getDouble("spawn.f"),
            (float) core.getConfig().getDouble("spawn.t"));

    private final Location center = new Location(Bukkit.getWorld("game"),
            (float) core.getConfig().getDouble("maps." + core.world + ".center.x"),
            (float) core.getConfig().getDouble("maps." + core.world + ".center.y"),
            (float) core.getConfig().getDouble("maps." + core.world + ".center.z"));

    private final Location spec = new Location(Bukkit.getWorld("game"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spec.x"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spec.y"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spec.z"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spec.f"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spec.t"));



    public Location getSpawn() { return this.spawn; }
    public Location getCenter() { return this.center; }
    public Location getSpec() { return this.spec; }
    public Location getPlayerSpawn(String spawn)
    {
        Location loc = new Location(Bukkit.getWorld("game"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spawns." + spawn + ".x"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spawns." + spawn + ".y"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spawns." + spawn + ".z"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spawns." + spawn + ".f"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spawns." + spawn + ".t"));
        return loc;
    }

    public String convertTime(int timeInSeconds)
    {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02dm %02ds", minutes, seconds);
    }

    public void dropChestContents(Block block, Inventory inventory) {
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() == Material.AIR) {
                block.getWorld().dropItemNaturally(block.getLocation(), item);
            }
        }
    }

    public void clearAndDropExceptCompasses(Player player)
    {
        for (ItemStack item : player.getInventory().getContents())
        {
            if (item != null && item.getType() != Material.AIR && item.getType() != Material.COMPASS)
            {
                player.getWorld().dropItem(player.getLocation(), item);
            }
        }
        player.getInventory().clear();
    }


}
