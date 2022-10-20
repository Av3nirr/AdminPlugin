package fr.av3nirr;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("§aPLugin Allumé");
        setCommands();
        getServer().getPluginManager().registerEvents(new Events(), this);


    }
    private static Main instance;
    public static Main getInstance(){
        return instance;
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void setCommands(){
        this.getCommand("admin").setExecutor(new CmdAdmin());
    }
}
