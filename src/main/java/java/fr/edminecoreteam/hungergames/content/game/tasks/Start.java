package fr.edminecoreteam.hungergames.content.game.tasks;

import fr.edminecoreteam.api.EdmineAPI;
import fr.edminecoreteam.hungergames.Core;
import fr.edminecoreteam.hungergames.State;
import fr.edminecoreteam.hungergames.content.game.Game;
import fr.edminecoreteam.hungergames.utils.game.GameUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Start extends BukkitRunnable
{
    public int timer;
    private final Core core;
    private final GameUtils gameUtils = new GameUtils();

    public Start(Core core)
    {
        this.core = core;
        this.timer = core.getConfig().getInt("timers.game");
    }

    public void run()
    {
        if (!core.isState(State.INGAME)) { cancel(); }
        core.timers(timer);
        EdmineAPI.getInstance().getBossBarBuilder().setTitle("§fTemps restant: §b" + gameUtils.convertTime(timer));
        EdmineAPI.getInstance().getBossBarBuilder().setHealth(timer, core.getConfig().getInt("timers.game"));

        if (core.getPlayersInGame().size() == 1)
        {
            core.setState(State.FINISH);
            Game game = new Game();
            game.endGame();
            cancel();
        }

        for (Player pls : core.getServer().getOnlinePlayers()) {
            if (timer <= 15 && timer != 5 && timer != 4 && timer != 3 && timer != 2 && timer != 1) {
                pls.playSound(pls.getLocation(), Sound.NOTE_STICKS, 1.0f, 1.0f);
            }
        }
        if (timer == 5)
        {
            for (Player pls : core.getServer().getOnlinePlayers()) {
                pls.sendTitle("§a§l" + timer, "");
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
            core.setState(State.FINISH);
            Game game = new Game();
            game.endGame();
            cancel();
        }
        --timer;
    }
}
