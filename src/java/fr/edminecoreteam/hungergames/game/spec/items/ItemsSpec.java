package fr.edminecoreteam.hungergames.game.spec.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemsSpec
{
    private final Player p;

    public ItemsSpec(Player p)
    {
        this.p = p;
    }

    public void get()
    {
        ItemStack spec = new ItemStack(Material.COMPASS, 1);
        ItemMeta specM = spec.getItemMeta();
        specM.setDisplayName("§e§lNavigation §7• Clique");
        spec.setItemMeta(specM);
        p.getInventory().setItem(0, spec);

        ItemStack settings = new ItemStack(Material.REDSTONE_COMPARATOR, 1);
        ItemMeta settingsM = settings.getItemMeta();
        settingsM.setDisplayName("§9§lRéglages §7• Clique");
        settings.setItemMeta(settingsM);
        p.getInventory().setItem(1, settings);

        ItemStack newGame = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta newGameM = newGame.getItemMeta();
        newGameM.setDisplayName("§d§lRejouer §7• Clique");
        newGame.setItemMeta(newGameM);
        p.getInventory().setItem(4, newGame);

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
