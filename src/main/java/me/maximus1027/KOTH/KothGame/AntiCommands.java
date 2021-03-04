package me.maximus1027.KOTH.KothGame;

import me.maximus1027.KOTH.Main;
import me.maximus1027.KOTH.Utils.ColorCoding;
import me.maximus1027.KOTH.Utils.PlayerUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class AntiCommands implements Listener {
    private Main plugin;
    public AntiCommands(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){
        if(e.getPlayer().hasPermission("koth.admin")){
            return;
        }

        if(PlayerUtils.PlayerInGame(e.getPlayer()) && KothGame.inProgress){
            e.setCancelled(true);
            e.getPlayer().sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("anti-command")));
        }
    }
}
