package fr.av3nirr;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class CmdAdmin implements CommandExecutor {
    public static ArrayList<Player> VanishedPlayers = new ArrayList<>();
    public static ArrayList<Player> Admins = new ArrayList<>();
    public static ArrayList<String> vanishLore = new ArrayList<>();
    public static ItemStack kickItem = new ItemStack(Material.STICK);
    public  static ItemStack vanishItem = new ItemStack(Material.BLAZE_POWDER);
    public static ItemStack flyItem = new ItemStack(Material.LEAD);
    public static ItemMeta vanishMeta = vanishItem.getItemMeta();
    public static ItemMeta flyMeta = flyItem.getItemMeta();


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)){
            System.out.println("Erreur, vous n'êtes pas un joueur !");
            return false;
        }

        Player p = (Player) sender;

        if(Admins.contains(p)){
            VanishedPlayers.remove(p);
            Admins.remove(p);
            p.getInventory().clear();
            for (Player allPlayer : sender.getServer().getOnlinePlayers()){
                allPlayer.showPlayer(JavaPlugin.getPlugin(Main.class), p);
            }
            p.setAllowFlight(false);
            p.setInvulnerable(false);
            p.sendMessage("§aTu viens de désactiver le mode admin !");
        }else{
            ItemMeta kickMeta = kickItem.getItemMeta();
            ArrayList<String> kickLore = new ArrayList<>();
            kickLore.add("&aPermet de bannir un joueur en faisant un clique droit dessus !");
            kickMeta.setLore(kickLore);
            kickMeta.setUnbreakable(true);
            kickMeta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
            kickMeta.setDisplayName(ChatColor.RED + "Baguette de bannissement");
            kickItem.setItemMeta(kickMeta);


            vanishLore.add("§aClique droit pour activer/désactiver le vanish");
            vanishLore.add("§fStatut: §aActivé");
            vanishMeta.setLore(vanishLore);
            vanishMeta.setDisplayName("§cVanish");
            vanishItem.setItemMeta(vanishMeta);
            VanishedPlayers.add(p);
            Admins.add(p);
            for (Player allPlayer : sender.getServer().getOnlinePlayers()){
                allPlayer.hidePlayer(JavaPlugin.getPlugin(Main.class), p);
            }
            p.setAllowFlight(true);
            p.setInvulnerable(true);
            p.sendMessage("§aTu viens de passer en mode Admin.");
            p.getInventory().setItem(0, kickItem);
            p.getInventory().setItem(1, vanishItem);
        }
        return true;
    }
}
