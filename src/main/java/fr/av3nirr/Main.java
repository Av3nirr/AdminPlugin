package fr.av3nirr;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Main extends JavaPlugin {
    public ArrayList<Player> Admins = new ArrayList<>();
    public ArrayList<String> vanishLore = new ArrayList<>();
    public ArrayList<Player> VanishedPlayers = new ArrayList<>();
    public ItemStack kickItem = new ItemStack(Material.STICK);
    public ItemStack vanishItem = new ItemStack(Material.BLAZE_POWDER);
    public ItemStack flyItem = new ItemStack(Material.LEAD);
    public ItemMeta vanishMeta = vanishItem.getItemMeta();
    public ItemMeta flyMeta = flyItem.getItemMeta();

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
