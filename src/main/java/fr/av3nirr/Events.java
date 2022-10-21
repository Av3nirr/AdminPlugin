package fr.av3nirr;

import net.kyori.adventure.title.Title;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.Objects;

public class Events implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if (Main.getInstance().Admins.contains(p)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (Main.getInstance().Admins.contains(p)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public  void onPearl(ProjectileLaunchEvent e){
        Player p = (Player) e.getEntity().getShooter();
        if (Main.getInstance().Admins.contains(p)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        for(Player admin : Main.getInstance().VanishedPlayers){
            player.hidePlayer(JavaPlugin.getPlugin(Main.class), admin);
        }

    }
    @EventHandler
    public void onPlayerMoove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if(Main.getInstance().freezedPlayers.contains(p)){
            e.setCancelled(true);
            p.sendTitle("§bTu es Freeze !", "§fVa sur le discord: discord", 1, 10, 1);
        }
    }
    @EventHandler
    public  void onRightClick(PlayerInteractAtEntityEvent e){
        Player p = e.getPlayer();
        Player target = (Player) e.getRightClicked();
        Player freezeTarget = (Player) p.getServer().getOfflinePlayer(target.getUniqueId());
        //La condition ne s'enclanche pas avec un ==, il faut tester avec un .equals()
        if (Objects.equals(p.getItemInHand(), Main.getInstance().kickItem)) {
            target.kick();
            p.sendMessage("§aVous avez correctement kick §r" + target.getName() + " §adu serveur !");
        }else if(Objects.equals(p.getItemInHand(), Main.getInstance().freezeItem)){
            Main.getInstance().freezedPlayers.add(freezeTarget);
            target.sendMessage("§bVous venez d'être Freeze par §e" + p.getName());
            p.sendMessage("§aVous venez de Freeze §e" + target.getName());
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
                Main.getInstance().vanishLore.add("§fStatut: §cDésactivé");
                Main.getInstance().vanishMeta.setLore(Main.getInstance().vanishLore);
                Main.getInstance().vanishItem.setItemMeta(Main.getInstance().vanishMeta);
                p.getInventory().setItem(1, Main.getInstance().vanishItem);
                Main.getInstance().VanishedPlayers.remove(p);
                for (Player allPlayer : p.getServer().getOnlinePlayers()){
                    allPlayer.showPlayer(JavaPlugin.getPlugin(Main.class), p);
                }
            }
        } else if (Objects.equals(p.getItemInHand(), Main.getInstance().flyItem)) {
            if(Main.getInstance().FlyingPlayers.contains(p)){
                Main.getInstance().FlyingPlayers.remove(p);
                p.setFlying(false);
                p.setAllowFlight(false);
                p.sendMessage("§cFly désactivé !");
            }else{
                Main.getInstance().FlyingPlayers.add(p);
                p.setAllowFlight(true);
                p.sendMessage("§aFly activé !");
            }
        } else if (Objects.equals(p.getItemInHand(), Main.getInstance().randomTpItem)){
            int nombreAleatoire = (int) (Math.random() * ((p.getServer().getOnlinePlayers().size()) + 1));
            Player target = (Player) p.getServer().getOnlinePlayers().toArray()[nombreAleatoire];
            p.teleport(target);
            p.sendMessage("§cTu as été téléporté au joueur §f" + target.getName());
        }
    }
}
