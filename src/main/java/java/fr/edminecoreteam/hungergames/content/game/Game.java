package fr.edminecoreteam.hungergames.content.game;

import fr.edminecoreteam.hungergames.Core;
import fr.edminecoreteam.hungergames.content.game.eventzones.EventTask;
import fr.edminecoreteam.hungergames.content.end.End;
import fr.edminecoreteam.hungergames.content.game.kits.DefaultItem;
import fr.edminecoreteam.hungergames.content.game.tasks.NoPVP;
import fr.edminecoreteam.hungergames.content.game.tasks.Preparation;
import fr.edminecoreteam.hungergames.content.game.tasks.Start;
import fr.edminecoreteam.hungergames.utils.game.GameUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Game
{
    private static final Core core = Core.getInstance();
    private final GameUtils gameUtils = new GameUtils();

    public void preparation()
    {
        for (String s : core.getConfig().getConfigurationSection("maps." + core.world + ".spawns").getKeys(false))
        {
            if (core.getPlayersToSpawn().size() > 0)
            {
                Player p = core.getPlayersToSpawn().get(0);
                Location pLoc = gameUtils.getPlayerSpawn(s);

                p.getInventory().clear();
                p.teleport(pLoc);
                core.spawnListeners().setPlayerLocation(p, pLoc);
                core.getPlayersToSpawn().remove(p);
            }
        }

        Preparation task = new Preparation(core);
        task.runTaskTimer((Plugin) core, 0L, 20L);
    }

    public void nopvp()
    {
        DefaultItem defaultItem = new DefaultItem();
        for (Player pls : core.getServer().getOnlinePlayers())
        {
            if (core.getPlayersInGame().contains(pls.getName()))
            {
                defaultItem.get(pls);
            }
        }

        NoPVP task = new NoPVP(core);
        task.runTaskTimer((Plugin) core, 0L, 20L);

        EventTask task2 = new EventTask(core);
        task2.runTaskTimer((Plugin) core, 0L, 20L);
    }

    public void game()
    {
        Start task = new Start(core);
        task.runTaskTimer((Plugin) core, 0L, 20L);
    }

    public void endGame()
    {
        for (Player pls : core.getServer().getOnlinePlayers())
        {
            if (core.getSpec().getSpec().contains(pls))
            {
                core.getSpec().removeSpec(pls);
            }
        }
        End end = new End();
        end.end();
    }
}
