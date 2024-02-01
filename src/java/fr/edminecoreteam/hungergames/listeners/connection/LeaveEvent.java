package fr.edminecoreteam.hungergames.listeners.connection;

import fr.edminecoreteam.hungergames.Core;
import fr.edminecoreteam.hungergames.State;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener
{
    private final static Core core = Core.getInstance();

    @EventHandler
    public void event(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        e.setQuitMessage(null);
        if (core.isState(State.WAITING))
        {
            if (core.getPlayersInGame().contains(p.getName()))
            {
                core.getPlayersInGame().remove(p.getName());
                int finalcount = core.getServer().getOnlinePlayers().size() - 1;
                core.getServer().broadcastMessage("§e" + p.getName() + "§7 a quitté le jeu. §d" + finalcount + "§d/" + core.getMaxplayers());
            }
        }
        if (core.isState(State.STARTING))
        {
            if (core.getPlayersInGame().contains(p.getName()))
            {
                core.getPlayersInGame().remove(p.getName());
                int finalcount = core.getServer().getOnlinePlayers().size() - 1;
                core.getServer().broadcastMessage("§e" + p.getName() + "§7 a quitté le jeu. §d" + finalcount + "§d/" + core.getMaxplayers());
            }
        }
        if (core.isState(State.INGAME) || core.isState(State.PREPARATION) || core.isState(State.NOPVP))
        {

        }
    }
}
