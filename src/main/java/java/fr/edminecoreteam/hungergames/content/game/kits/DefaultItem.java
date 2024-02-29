package fr.edminecoreteam.hungergames.content.game.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DefaultItem
{
    public void get(Player p)
    {
        ItemStack itemStack = new ItemStack(Material.COMPASS, 1);
        ItemMeta itemStackM = itemStack.getItemMeta();
        itemStackM.setDisplayName("§aBoussole");
        itemStack.setItemMeta(itemStackM);
        p.getInventory().setItem(8, itemStack);
    }
}
