package fr.edminecoreteam.hungergames;

import fr.edminecoreteam.api.EdmineAPI;
import fr.edminecoreteam.api.management.ChestManager;
import fr.edminecoreteam.api.management.WorldManager;
import fr.edminecoreteam.hungergames.listeners.content.game.GameListeners;
import fr.edminecoreteam.hungergames.content.game.eventzones.LoadZones;
import fr.edminecoreteam.hungergames.content.game.spec.Spec;
import fr.edminecoreteam.hungergames.listeners.connection.JoinEvent;
import fr.edminecoreteam.hungergames.listeners.connection.LeaveEvent;
import fr.edminecoreteam.hungergames.utils.game.GameUtils;
import fr.edminecoreteam.hungergames.utils.game.SpawnListeners;
import fr.edminecoreteam.hungergames.utils.minecraft.TitleBuilder;
import fr.edminecoreteam.hungergames.utils.scoreboards.JoinScoreboardEvent;
import fr.edminecoreteam.hungergames.utils.scoreboards.LeaveScoreboardEvent;
import fr.edminecoreteam.hungergames.utils.scoreboards.ScoreboardManager;
import fr.edminecoreteam.hungergames.utils.scoreboards.WorldChangeScoreboardEvent;
import fr.edminecoreteam.hungergames.listeners.content.waiting.WaitingListeners;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Core extends JavaPlugin
{
    private static Core instance;
    private State state;
    private static Plugin plugin;
    public String world;
    public int timers;
    public void timers(int i) { this.timers = i; }
    private int maxplayers;
    private List<String> playersInGame;
    private List<Player> playersToSpawn;
    private Spec spec;
    public boolean isForceStart = false;
    private SpawnListeners spawnListeners;
    private LoadZones loadZones;
    private TitleBuilder title;
    private ScoreboardManager scoreboardManager;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void onEnable()
    {
        instance = this;
        saveDefaultConfig();
        loadGameWorld();
        loadListeners();
        ScoreboardManager();
        //MySQLConnect();

        setState(State.WAITING);
    }

    private void loadListeners()
    {
        this.spawnListeners = new SpawnListeners();
        this.playersInGame = new ArrayList<>();
        this.playersToSpawn = new ArrayList<>();
        this.title = new TitleBuilder();
        this.spec = new Spec();
        this.loadZones = new LoadZones();
        this.loadZones.init();
        EdmineAPI.getInstance().getBossBarBuilder().setTitle("§8● §6§lHungerGames §8●");
        EdmineAPI.getInstance().getBossBarBuilder().setHealth(100, 100);
        for (String s : this.getConfig().getConfigurationSection("maps." + this.world + ".spawns").getKeys(false))
        {
            ++this.maxplayers;
        }

        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new LeaveEvent(), this);

        Bukkit.getPluginManager().registerEvents(new WaitingListeners(), this);
        Bukkit.getPluginManager().registerEvents(new GameListeners(), this);
    }

    private void loadGameWorld()
    {
        System.out.println("Generating Game world...");
        WorldManager worldManager = new WorldManager();
        String world = worldManager.getRandomSubfolderName("gameTemplate/");
        worldManager.createGameWorld(world);
        this.world = world;
        System.out.println("Game world create.");

        System.out.println("Generating CHEST Location...");
        ChestManager chestManager = new ChestManager();
        GameUtils gameUtils = new GameUtils();
        chestManager.placeChestsAroundLocation(gameUtils.getCenter(), 5, this.getConfig().getInt("chests.radius"), this.getConfig().getInt("chests.number"));
        chestManager.fillAllChestsRandomlyInAWorld(Bukkit.getWorld("game"), gameUtils.commonItems(), gameUtils.rareItems(), 8, 3);
        System.out.println("All CHEST are ready !");
    }

    private void ScoreboardManager()
    {
        Bukkit.getPluginManager().registerEvents(new JoinScoreboardEvent(), this);
        Bukkit.getPluginManager().registerEvents(new LeaveScoreboardEvent(), this);
        Bukkit.getPluginManager().registerEvents(new WorldChangeScoreboardEvent(), this);

        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager();
    }

    public void setState(State state) { this.state = state; }
    public boolean isState(State state) { return this.state == state; }
    public ScoreboardManager getScoreboardManager() {
        return this.scoreboardManager;
    }
    public ScheduledExecutorService getExecutorMonoThread() {
        return this.executorMonoThread;
    }
    public ScheduledExecutorService getScheduledExecutorService() {
        return this.scheduledExecutorService;
    }
    public SpawnListeners spawnListeners() { return this.spawnListeners; }
    public List<String> getPlayersInGame() { return this.playersInGame; }
    public List<Player> getPlayersToSpawn() { return this.playersToSpawn; }
    public Spec getSpec() { return this.spec; }
    public LoadZones getLoadZones() { return this.loadZones; }
    public TitleBuilder titleBuilder() { return this.title; }
    public int getMaxplayers() { return this.maxplayers; }
    public static Core getInstance() { return Core.instance; }
    public static Plugin getPlugin() { return Core.plugin; }
}
