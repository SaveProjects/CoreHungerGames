package fr.edminecoreteam.hungergames.game.spec;

import fr.edminecoreteam.hungergames.Core;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Spec extends BukkitRunnable
{
    private final static Core core = Core.getInstance();
    private final List<Player> specs = new ArrayList<Player>();

    public Spec()
    {
        runTaskTimer(core, 0, 10);
    }

    public void setSpec(Player p)
    {
        specs.add(p);
    }

    public void removeSpec(Player p)
    {
        specs.remove(p);
        for (Player pls : core.getServer().getOnlinePlayers())
        {
            pls.showPlayer(p);
        }
    }

    @Override
    public void run()
    {
        for (Player pls : core.getServer().getOnlinePlayers())
        {
            if (!specs.contains(pls))
            {
                for (Player toHide : specs)
                {
                    pls.hidePlayer(toHide);
                }
            }
        }
    }

    public final List<Player> getSpec() { return this.specs; }
}
