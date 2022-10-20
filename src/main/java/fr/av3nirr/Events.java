package fr.av3nirr;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Events implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        for(Player admin : Main.getInstance().VanishedPlayers){
            player.hidePlayer(JavaPlugin.getPlugin(Main.class), admin);
        }

    }
    @EventHandler
    public  void onRightClick(PlayerInteractAtEntityEvent e){
        Player p = e.getPlayer();
        Player target = (Player) e.getRightClicked();
        //La condition ne s'enclanche pas avec un ==, il faut tester avec un .equals()
        if (Objects.equals(p.getItemInHand(), Main.getInstance().kickItem)) {
            target.kick();
            p.sendMessage("§Vous avez correctement kick §r" + target.getName() + " §adu serveur !");
        }
    }
    @EventHandler
    public void onClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (Objects.equals(p.getItemInHand(), Main.getInstance().vanishItem)){
            if (!(Main.getInstance().VanishedPlayers.contains(p))){
                Main.getInstance().VanishedPlayers.add(p);
                Main.getInstance().vanishLore.clear();
                Main.getInstance().vanishLore.add("§aClique droit pour activer/désactiver le vanish");
                Main.getInstance().vanishLore.add("§fStatut: §aActivé");
                Main.getInstance().vanishMeta.setLore(Main.getInstance().vanishLore);
                Main.getInstance().vanishItem.setItemMeta(Main.getInstance().vanishMeta);
                p.getInventory().setItem(1, Main.getInstance().vanishItem);
                for (Player allPlayer : p.getServer().getOnlinePlayers()){
                    allPlayer.hidePlayer(JavaPlugin.getPlugin(Main.class), p);
                }
            }else {
                Main.getInstance().vanishLore.clear();
                Main.getInstance().vanishLore.add("§aClique droit pour activer/désactiver le vanish");
                Main.getInstance().vanishLore.add("§fStatut: §aDésactivé");
                Main.getInstance().vanishMeta.setLore(Main.getInstance().vanishLore);
                Main.getInstance().vanishItem.setItemMeta(Main.getInstance().vanishMeta);
                p.getInventory().setItem(1, Main.getInstance().vanishItem);
                Main.getInstance().VanishedPlayers.remove(p);
                for (Player allPlayer : p.getServer().getOnlinePlayers()){
                    allPlayer.showPlayer(JavaPlugin.getPlugin(Main.class), p);
                }
            }
        }
    }
}
