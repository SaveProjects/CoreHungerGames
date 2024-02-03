package fr.edminecoreteam.hungergames;

import fr.edminecoreteam.hungergames.game.GameListeners;
import fr.edminecoreteam.hungergames.game.chests.RandomSpawnChests;
import fr.edminecoreteam.hungergames.game.chests.RefillChest;
import fr.edminecoreteam.hungergames.listeners.connection.JoinEvent;
import fr.edminecoreteam.hungergames.listeners.connection.LeaveEvent;
import fr.edminecoreteam.hungergames.utils.game.GameUtils;
import fr.edminecoreteam.hungergames.utils.game.SpawnListeners;
import fr.edminecoreteam.hungergames.utils.minecraft.TitleBuilder;
import fr.edminecoreteam.hungergames.utils.minecraft.edbossbar.BossBar;
import fr.edminecoreteam.hungergames.utils.minecraft.edbossbar.BossBarEvent;
import fr.edminecoreteam.hungergames.utils.scoreboards.JoinScoreboardEvent;
import fr.edminecoreteam.hungergames.utils.scoreboards.LeaveScoreboardEvent;
import fr.edminecoreteam.hungergames.utils.scoreboards.ScoreboardManager;
import fr.edminecoreteam.hungergames.utils.scoreboards.WorldChangeScoreboardEvent;
import fr.edminecoreteam.hungergames.utils.world.LoadWorld;
import fr.edminecoreteam.hungergames.waiting.WaitingListeners;
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
    public MySQL database;
    public String world;
    public int timers;
    public void timers(int i) { this.timers = i; }
    private int maxplayers;
    private List<String> playersInGame;
    private List<Player> playersToSpawn;
    public boolean isForceStart = false;
    private SpawnListeners spawnListeners;
    private BossBar bossBar;
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

    private void MySQLConnect()
    {
        (this.database = new MySQL(
                "jdbc:mysql://",
                this.getConfig().getString("mysql.host"),
                this.getConfig().getString("mysql.database"),
                this.getConfig().getString("mysql.user"),
                this.getConfig().getString("mysql.password")
        )).connexion();
    }

    private void loadListeners()
    {
        this.spawnListeners = new SpawnListeners();
        this.playersInGame = new ArrayList<String>();
        this.playersToSpawn = new ArrayList<Player>();
        this.title = new TitleBuilder();
        this.bossBar = new BossBar("§8● §6§lHungerGames §8●", 300);
        Bukkit.getPluginManager().registerEvents(new BossBarEvent(), this);
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
        String world = LoadWorld.getRandomSubfolderName("gameTemplate/");
        LoadWorld.createGameWorld(world);
        this.world = world;

        System.out.println("Generating CHEST Location...");
        RandomSpawnChests randomSpawnChests = new RandomSpawnChests();
        RefillChest refillChest = new RefillChest();
        GameUtils gameUtils = new GameUtils();
        randomSpawnChests.placeChestsAroundLocation(
                gameUtils.getCenter(),
                5,
                this.getConfig().getInt("chests.radius"),
                this.getConfig().getInt("chests.number"));
        refillChest.fillAllChestsRandomly();
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
    public BossBar getBossBar() { return this.bossBar; }
    public TitleBuilder titleBuilder() { return this.title; }
    public int getMaxplayers() { return this.maxplayers; }
    public static Core getInstance() { return Core.instance; }
    public static Plugin getPlugin() { return Core.plugin; }
}
