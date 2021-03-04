package me.maximus1027.KOTH.Utils;

import me.maximus1027.KOTH.Main;
import org.bukkit.entity.Player;

public class HelpPage {
    private Main plugin;
    public HelpPage(Main plugin){
        this.plugin = plugin;
    }

    public void sendHelpPage(Player plr){
        plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("help-1")));
        plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("help-2")));
        plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("help-3")));
        plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("help-4")));
        plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("help-5")));
        plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("help-6")));
        plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("help-7")));
        plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("help-8")));
    }
}
