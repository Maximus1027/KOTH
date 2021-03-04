package me.maximus1027.KOTH.KothGame;

import me.maximus1027.KOTH.Main;
import me.maximus1027.KOTH.Utils.ColorCoding;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class PlayerRemoveManager implements Listener {
    private Main plugin;
    public PlayerRemoveManager(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        HashMap<Player, Integer> PlayersPoints = KothGame.playerPoints;
        if(PlayersPoints.containsKey(e.getEntity())){
            e.getEntity().setLevel(0);
            PlayersPoints.remove(e.getEntity());

            if(KothGame.inProgress) {
                Bukkit.broadcastMessage(ColorCoding.changeColor(plugin.getConfig().getString("player-eliminated").replaceAll("%player%", e.getEntity().getName()).replaceAll("%playersleft%", String.valueOf(PlayersPoints.keySet().size()))));
            }
            if(PlayersPoints.keySet().size() == 1){
                new KothGame().stopGame();
            }
        }
    }


    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        HashMap<Player, Integer> PlayersPoints = KothGame.playerPoints;
        if(PlayersPoints.containsKey(e.getPlayer())){
            e.getPlayer().setLevel(0);
            PlayersPoints.remove(e.getPlayer());

            if(KothGame.inProgress) {
                Bukkit.broadcastMessage(ColorCoding.changeColor(plugin.getConfig().getString("player-eliminated").replaceAll("%player%", e.getPlayer().getName()).replaceAll("%playersleft%", String.valueOf(PlayersPoints.keySet().size()))));
            }
            if(PlayersPoints.keySet().size() == 1){
                new KothGame().stopGame();
            }
        }
    }
}
