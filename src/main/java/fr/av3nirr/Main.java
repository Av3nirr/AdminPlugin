package fr.av3nirr;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Main extends JavaPlugin {

    //Class instance
    private static Main INSTANCE;

    //Array
    public ArrayList<Player> Admins = new ArrayList<>();
    public ArrayList<String> vanishLore = new ArrayList<>();
    public ArrayList<Player> VanishedPlayers = new ArrayList<>();

    //Item
    public ItemStack kickItem = new ItemStack(Material.STICK);
    public ItemStack vanishItem = new ItemStack(Material.BLAZE_POWDER);
    public ItemStack flyItem = new ItemStack(Material.LEAD);

    //Meta
    public ItemMeta vanishMeta = vanishItem.getItemMeta();
    public ItemMeta flyMeta = flyItem.getItemMeta();

    @Override
    public void onEnable() {
        // Assignation d'une valeur à INSTANCE
        INSTANCE = this;

        // Registration de tes events et commandes
        setCommands();
        setListeners();

        //Mettre ce message une fois que le plugin est fini d'etre enable
        System.out.println("§aPLugin Allumé");
    }

    public static Main getInstance(){
        return INSTANCE;
    }
    @Override
    public void onDisable() {
        // Petit message d'aurevoir
        System.out.println(ChatColor.RED + "§aAdminPlugin Master disabling tranquillou bidou");
    }
    public void setCommands(){
        this.getCommand("admin").setExecutor(new CmdAdmin());
    }

    /*
    Quand tu auras + de listeners c'est plus simple de stocker
    ton PluginManager dans une variable
     */
    public void setListeners(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new Events(), this);
    }
}