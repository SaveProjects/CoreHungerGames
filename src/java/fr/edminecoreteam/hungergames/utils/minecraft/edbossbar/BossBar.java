package fr.edminecoreteam.hungergames.utils.minecraft.edbossbar;

import fr.edminecoreteam.hungergames.Core;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BossBar extends BukkitRunnable
{
    private String title;
    private double health;

    private final HashMap<Player, Wither> withers = new HashMap<Player, Wither>();
    private final List<Player> init = new ArrayList<Player>();

    private final static Core core = Core.getInstance();

    public BossBar(String title, double health)
    {
        this.title = title;
        this.health = health;
        runTaskTimer(core, 0, 10);
    }

    public void putPlayer(Player p)
    {
        Location location = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
        Location toSpawn = location.add(location.getDirection().multiply(5));
        Wither wither = (Wither) core.getServer().getWorld(p.getWorld().getName()).spawnEntity(toSpawn, EntityType.WITHER);

        wither.setCustomName(title);
        wither.setHealth(health);
        wither.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
        wither.setCanPickupItems(false);
        noAI(wither);
        withers.put(p, wither);
        init.add(p);
        new BukkitRunnable() {
            int t = 0;
            int f = 0;
            public void run() {

                ++t;
                ++f;

                if (f == 10)
                {
                    init.remove(p);
                }

                if (t == 1) {
                    run();
                }
            }
        }.runTaskTimer((Plugin) core, 0L, 5L);
    }

    public void removePlayer(Player p)
    {
        Wither wither = withers.get(p);
        withers.remove(p);
        wither.remove();
        if (init.contains(p)) { init.remove(p); }
    }

    public void setTitle(String title)
    {
        this.title = title;
        for (Map.Entry<Player, Wither> en : withers.entrySet())
        {
            Wither wither = en.getValue();
            wither.setCustomName(title);
        }
    }

    public void setHealth(int health, int maxHealth)
    {
        if (health == 0)
        {
            this.health = health + 1;
            double newhealth = health + 1;
            for (Map.Entry<Player, Wither> en : withers.entrySet())
            {
                Wither wither = en.getValue();
                wither.setHealth(getPercentage(health, maxHealth));
            }
        }
        else
        {
            this.health = health;
            for (Map.Entry<Player, Wither> en : withers.entrySet())
            {
                Wither wither = en.getValue();
                wither.setHealth(getPercentage(health, maxHealth));
            }
        }
    }

    private double getPercentage(int currentNumber, int hundredPercentNumber)
    {
        // Vérification pour éviter la division par zéro
        if (hundredPercentNumber == 0) {
            throw new IllegalArgumentException("Le nombre représentant 100% ne peut pas être zéro.");
        }

        // Calcul du pourcentage
        double percentage = ((double) currentNumber / (double) hundredPercentNumber) * 300;
        return percentage;
    }

    private void noAI(Entity bukkitEntity)
    {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null)
        {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        tag.setBoolean("Silent", true);
        tag.setBoolean("Visible", false);
        nmsEntity.f(tag);
    }

    public Location getWitherLocation(Location l)  {
        return l.add(l.getDirection().multiply(-15));
        //return new Location(l.getWorld(), l.getX(), l.getY() + 15, l.getZ());
    }

    public Location getWitherLocationInit(Location l)  {
        return l.add(l.getDirection().multiply(15));
    }

    @Override
    public void run() {
        for (Map.Entry<Player, Wither> en : withers.entrySet())
        {
            Player player = en.getKey();
            Wither wither = en.getValue();
            Location loc = getWitherLocation(player.getLocation());
            if (init.contains(player))
            {
                Location locInit = getWitherLocationInit(player.getLocation());
                wither.teleport(locInit);
            }
            else
            {
                wither.teleport(loc);
            }
            for(Player on : core.getServer().getOnlinePlayers())
            {
                if (!on.getName().equalsIgnoreCase(player.getName()))
                {
                    PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(wither.getEntityId());
                    ((CraftPlayer) on).getHandle().playerConnection.sendPacket(packet);
                }
            }
        }
    }
}
