package fr.edminecoreteam.hungergames.waiting.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemsWaiting
{
    private final Player p;

    public ItemsWaiting(Player p)
    {
        this.p = p;
    }

    public void get()
    {
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
