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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class CmdAdmin implements CommandExecutor {




    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)){
            System.out.println("Erreur, vous n'êtes pas un joueur !");
            return false;
        }

        Player p = (Player) sender;

        if(Main.getInstance().Admins.contains(p)){
            Main.getInstance().VanishedPlayers.remove(p);
            Main.getInstance().Admins.remove(p);
            p.removePotionEffect(PotionEffectType.GLOWING);
            p.getInventory().clear();
            for (Player allPlayer : sender.getServer().getOnlinePlayers()){
                allPlayer.showPlayer(JavaPlugin.getPlugin(Main.class), p);
            }
            p.setAllowFlight(false);
            p.setInvulnerable(false);
            p.sendMessage("§aTu viens de désactiver le mode admin !");
        }else{
            ItemMeta kickMeta = Main.getInstance().kickItem.getItemMeta();
            ArrayList<String> kickLore = new ArrayList<>();
            kickLore.add("&aPermet de kick un joueur en faisant un clique droit dessus !");
            kickMeta.setLore(kickLore);
            kickMeta.setUnbreakable(true);
            kickMeta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
            kickMeta.setDisplayName(ChatColor.RED + "Baguette de kick");
            Main.getInstance().kickItem.setItemMeta(kickMeta);

            Main.getInstance().flyMeta.setUnbreakable(true);
            Main.getInstance().flyMeta.setDisplayName("§eBaguette de vol");
            Main.getInstance().vanishLore.add("§aClique droit pour activer/désactiver le vol");
            Main.getInstance().flyItem.setItemMeta(Main.getInstance().flyMeta);



            Main.getInstance().vanishLore.add("§aClique droit pour activer/désactiver le vanish");
            Main.getInstance().vanishLore.add("§fStatut: §aActivé");
            Main.getInstance().vanishMeta.setLore(Main.getInstance().vanishLore);
            Main.getInstance().vanishMeta.setDisplayName("§cVanish");
            Main.getInstance().vanishItem.setItemMeta(Main.getInstance().vanishMeta);
            Main.getInstance().VanishedPlayers.add(p);
            Main.getInstance().Admins.add(p);
            for (Player allPlayer : sender.getServer().getOnlinePlayers()){
                allPlayer.hidePlayer(JavaPlugin.getPlugin(Main.class), p);
            }
            Main.getInstance().FlyingPlayers.add(p);
            p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 300000000, 1));
            p.setAllowFlight(true);
            p.setInvulnerable(true);
            p.sendMessage("§aTu viens de passer en mode Admin.");
            p.getInventory().setItem(0, Main.getInstance().kickItem);
            p.getInventory().setItem(1, Main.getInstance().vanishItem);
            p.getInventory().setItem(2, Main.getInstance().flyItem);
        }
        return true;
    }
}
