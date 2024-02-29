package fr.edminecoreteam.hungergames.content.game.eventzones;

import fr.edminecoreteam.hungergames.Core;
import fr.edminecoreteam.hungergames.State;
import fr.edminecoreteam.hungergames.content.game.eventzones.events.RainPoison;
import fr.edminecoreteam.hungergames.utils.game.GameUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventTask extends BukkitRunnable
{
    int timer;
    private final Core core;
    private final GameUtils gameUtils = new GameUtils();

    public EventTask(Core core)
    {
        this.core = core;
        this.timer = 45;
    }

    public void run()
    {
        if (!core.isState(State.INGAME) && !core.isState(State.NOPVP)) { cancel(); }
        for (Player pls : core.getServer().getOnlinePlayers()) { gameUtils.getActionBarZone(pls); }

        if (timer == 0)
        {
            core.getLoadZones().zoneInEvent().clear();
            core.getLoadZones().getZones().clear();
            core.getLoadZones().init();
            List<String> get = selectTwoZone(core.getLoadZones().getZones());
            timer = 45;
            for (String s : get)
            {
                core.getLoadZones().zoneInEvent().add(s);
                startRandomEvent(s);
            }
            for (Player pls : core.getServer().getOnlinePlayers())
            {
                pls.playSound(pls.getLocation(), Sound.WITHER_SPAWN, 1.0f, 1.0f);
                pls.sendTitle("", "§6Nouvelle zone d'événement détectée !");
            }
        }
        --timer;
    }

    private List<String> selectTwoZone(List<String> list)
    {
        List<String> resultats = new ArrayList<>();
        Random random = new Random();

        if (2 > list.size())
        {
            throw new IllegalArgumentException("La liste ne contient pas suffisamment d'éléments...");
        }

        for (int i = 0; i < 2; i++)
        {
            int index = random.nextInt(list.size());
            resultats.add(list.get(index));
            list.remove(index);
        }

        return resultats;
    }

    private void startRandomEvent(String zone)
    {
        Random random = new Random();
        int index = random.nextInt(2);

        if (index == 1)
        {
            RainPoison event = new RainPoison(zone);
            event.init();
        }
    }
}
