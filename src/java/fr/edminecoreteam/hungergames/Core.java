package fr.edminecoreteam.hungergames;

import fr.edminecoreteam.hungergames.utils.game.SpawnListeners;
import fr.edminecoreteam.hungergames.utils.minecraft.BossBar;
import fr.edminecoreteam.hungergames.utils.minecraft.TitleBuilder;
import fr.edminecoreteam.hungergames.utils.scoreboards.JoinScoreboardEvent;
import fr.edminecoreteam.hungergames.utils.scoreboards.LeaveScoreboardEvent;
import fr.edminecoreteam.hungergames.utils.scoreboards.ScoreboardManager;
import fr.edminecoreteam.hungergames.utils.scoreboards.WorldChangeScoreboardEvent;
import fr.edminecoreteam.hungergames.utils.world.LoadWorld;
import org.bukkit.Bukkit;
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
        this.playersInGame = new ArrayList<String>();
        this.title = new TitleBuilder();
        this.bossBar = new BossBar(this, "HungerGames");
        this.maxplayers = 0;
    }

    private void loadGameWorld()
    {
        String world = LoadWorld.getRandomSubfolderName("gameTemplate/");
        LoadWorld.createGameWorld(world);
        this.world = world;
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
    public BossBar getBossBar() { return this.bossBar; }
    public TitleBuilder titleBuilder() { return this.title; }
    public int getMaxplayers() { return this.maxplayers; }
    public static Core getInstance() { return Core.instance; }
    public static Plugin getPlugin() { return Core.plugin; }
}
