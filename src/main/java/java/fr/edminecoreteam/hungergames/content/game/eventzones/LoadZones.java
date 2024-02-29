package fr.edminecoreteam.hungergames.content.game.eventzones;

import fr.edminecoreteam.hungergames.Core;

import java.util.ArrayList;
import java.util.List;

public class LoadZones
{
    private final static Core core = Core.getInstance();

    private final List<String> zones = new ArrayList<>();
    private final List<String> zonesInEvent = new ArrayList<>();

    public void init()
    {
        zones.addAll(core.getConfig().getConfigurationSection("maps." + core.world + ".zones").getKeys(false));
    }

    public List<String> getZones()
    {
        return this.zones;
    }

    public List<String> zoneInEvent() { return this.zonesInEvent; }
}
