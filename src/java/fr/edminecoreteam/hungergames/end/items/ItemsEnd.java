package fr.edminecoreteam.hungergames.end.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemsEnd
{
    private final Player p;

    public ItemsEnd(Player p)
    {
        this.p = p;
    }

    public void get()
    {
        ItemStack newGame = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta newGameM = newGame.getItemMeta();
        newGameM.setDisplayName("§d§lRejouer §7• Clique");
        newGame.setItemMeta(newGameM);
        p.getInventory().setItem(0, newGame);

        ItemStack leave = new ItemStack(Material.BED, 1);
        ItemMeta leaveM = leave.getItemMeta();
        leaveM.setDisplayName("§c§lQuitter §7• Clique");
        leave.setItemMeta(leaveM);
        p.getInventory().setItem(8, leave);
    }

    public void clearinv()
    {
        p.getInventory().clear();
    }
}
