package fr.edminecoreteam.hungergames.content.game.eventzones.events;

import fr.edminecoreteam.hungergames.Core;
import fr.edminecoreteam.hungergames.utils.game.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;


public class RainPoison
{
    private final static Core core = Core.getInstance();

    private final String zone;
    private final GameUtils gameUtils = new GameUtils();

    public RainPoison(String zone)
    {
        this.zone = zone;
    }

    public void init()
    {
        Location locA = new Location(Bukkit.getWorld("game"),
                (float) core.getConfig().getDouble("maps." + core.world + ".zones." + zone + ".pos1.x"),
                (float) core.getConfig().getDouble("maps." + core.world + ".zones." + zone + ".pos1.y"),
                (float) core.getConfig().getDouble("maps." + core.world + ".zones." + zone + ".pos1.z"));

        Location locB = new Location(Bukkit.getWorld("game"),
                (float) core.getConfig().getDouble("maps." + core.world + ".zones." + zone + ".pos2.x"),
                (float) core.getConfig().getDouble("maps." + core.world + ".zones." + zone + ".pos2.y"),
                (float) core.getConfig().getDouble("maps." + core.world + ".zones." + zone + ".pos2.z"));

        new BukkitRunnable() {
            int t = 0;
            public void run() {

                ++t;
                if (!core.getLoadZones().zoneInEvent().contains(zone))
                {
                    for (Player pls : core.getServer().getOnlinePlayers())
                    {
                        if (gameUtils.itsOnAorB(pls.getLocation(), locA, locB))
                        {
                            cancelAction(pls);
                        }
                    }
                    cancel();
                }

                for (Player pls : core.getServer().getOnlinePlayers())
                {
                    if (gameUtils.itsOnAorB(pls.getLocation(), locA, locB))
                    {
                        action(pls);
                    }
                    else
                    {
                        cancelAction(pls);
                    }
                }

                if (t == 1) {
                    run();
                }
            }
        }.runTaskTimer((Plugin) core, 0L, 15L);
    }

    private void action(Player p)
    {
        p.setPlayerTime(18000, false);
        p.setPlayerWeather(WeatherType.DOWNFALL);
        p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 2, 1, false, false));
    }

    private void cancelAction(Player p)
    {
        p.resetPlayerTime();
        p.resetPlayerWeather();
        p.removePotionEffect(PotionEffectType.POISON);
    }
}
