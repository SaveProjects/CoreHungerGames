package fr.edminecoreteam.hungergames.game.tasks;

import fr.edminecoreteam.hungergames.Core;
import fr.edminecoreteam.hungergames.State;
import fr.edminecoreteam.hungergames.game.Game;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class NoPVP extends BukkitRunnable
{
    public int timer;

    private final Core core;

    public NoPVP(Core core)
    {
        this.core = core;
        this.timer = core.getConfig().getInt("timers.nopvp");
    }

    public void run()
    {
        if (!core.isState(State.NOPVP)) { cancel(); }
        core.timers(timer);
        for (Player pls : core.getServer().getOnlinePlayers()) { pls.setLevel(timer); }
        core.getBossBar().setTitle("§fPVP actif dans: §c" + timer + "§cs");
        for (Player pls : core.getServer().getOnlinePlayers()) {
            if (timer <= 15 && timer != 5 && timer != 4 && timer != 3 && timer != 2 && timer != 1) {
                pls.playSound(pls.getLocation(), Sound.NOTE_STICKS, 1.0f, 1.0f);
            }
        }
        if (timer == 5)
        {
            for (Player pls : core.getServer().getOnlinePlayers()) {
                pls.sendTitle("§a§l" + timer, "§7Le PVP va être activé.");
                pls.playSound(pls.getLocation(), Sound.NOTE_STICKS, 1.0f, 1.5f);
            }
        }
        if (timer == 4)
        {
            for (Player pls : core.getServer().getOnlinePlayers()) {
                pls.sendTitle("§6§l" + timer, "");
                pls.playSound(pls.getLocation(), Sound.NOTE_STICKS, 1.0f, 1.2f);
            }
        }
        if (timer == 3)
        {
            for (Player pls : core.getServer().getOnlinePlayers()) {
                pls.sendTitle("§e§l" + timer, "");
                pls.playSound(pls.getLocation(), Sound.NOTE_STICKS, 1.0f, 1.0f);
            }
        }
        if (timer == 2)
        {
            for (Player pls : core.getServer().getOnlinePlayers()) {
                pls.sendTitle("§c§l" + timer, "");
                pls.playSound(pls.getLocation(), Sound.NOTE_STICKS, 1.0f, 0.7f);
            }
        }
        if (timer == 1)
        {
            for (Player pls : core.getServer().getOnlinePlayers()) {
                pls.sendTitle("§4§l" + timer, "");
                pls.playSound(pls.getLocation(), Sound.NOTE_STICKS, 1.0f, 0.5f);
            }
        }
        if (timer == 0)
        {
            core.setState(State.INGAME);
            for (Player pls : core.getServer().getOnlinePlayers()) {
                pls.sendTitle("", "§4§lPVP Actif !");
                pls.playSound(pls.getLocation(), Sound.ENDERDRAGON_HIT, 1.0f, 1.0f);
            }
            Game game = new Game();
            game.game();
            cancel();
        }

        --timer;
    }
}
