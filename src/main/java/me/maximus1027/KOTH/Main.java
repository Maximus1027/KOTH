package me.maximus1027.KOTH;

import me.maximus1027.KOTH.KothGame.AntiCommands;
import me.maximus1027.KOTH.KothGame.KothGame;
import me.maximus1027.KOTH.KothGame.KothWand;
import me.maximus1027.KOTH.KothGame.PlayerRemoveManager;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {



    @Override
    public void onEnable() {
        getCommand("kingofthehill").setExecutor(new KothGame(this));
        getCommand("kothwand").setExecutor(new KothWand(this));

        getServer().getPluginManager().registerEvents(new KothWand(this), this);
        getServer().getPluginManager().registerEvents(new PlayerRemoveManager(this), this);
        getServer().getPluginManager().registerEvents(new AntiCommands(this), this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

    }



}
