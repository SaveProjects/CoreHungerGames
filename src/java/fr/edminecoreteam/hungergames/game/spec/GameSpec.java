package fr.edminecoreteam.hungergames.game.spec;

import fr.edminecoreteam.hungergames.Core;
import fr.edminecoreteam.hungergames.game.spec.items.ItemsSpec;
import fr.edminecoreteam.hungergames.utils.game.GameUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GameSpec
{
    private static final Core core = Core.getInstance();
    private final GameUtils gameUtils = new GameUtils();
    public void setSpec(Player p)
    {
        p.getInventory().clear();
        p.getEquipment().setHelmet(null);
        p.getEquipment().setChestplate(null);
        p.getEquipment().setLeggings(null);
        p.getEquipment().setBoots(null);

        p.setGameMode(GameMode.ADVENTURE);
        p.setAllowFlight(true);
        p.setFlying(true);
        p.teleport(gameUtils.getSpec());
        ItemsSpec itemsSpec = new ItemsSpec(p);
        itemsSpec.get();
        if (core.getPlayersInGame().contains(p.getName()))
        {
            core.getPlayersInGame().remove(p.getName());
            core.getSpec().setSpec(p);
            p.sendTitle("§c§lPerdu...", "§7Peut-être une prochaine fois !");
            p.sendMessage("");
            p.sendMessage("  §d§lCompte rendu:");
            p.sendMessage("   §7• §c§lDéfaite...");
            p.sendMessage("   §7• §7Kills: §a...");
            p.sendMessage("   §7• §7Coffres pillés: §b...");
            p.sendMessage("");
            p.sendMessage(" §8➡ §fVisionnez vos statistiques sur votre profil.");
            p.sendMessage("");
        }
    }
}
