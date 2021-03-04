package me.maximus1027.KOTH.KothGame;

import me.maximus1027.KOTH.Main;
import me.maximus1027.KOTH.Utils.ColorCoding;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class KothWand implements CommandExecutor, Listener {
    public Location firstLocation;
    public Location secondLocation;

    public static Main plugin;
    public KothWand(Main plugin){
        this.plugin = plugin;
    }
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }
        Player plr = (Player) sender;
        if(!plr.hasPermission("koth.admin")){
            plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("no-permissions")));
            return false;
        }
        ItemStack wand = new ItemStack(Material.BONE);
        wand.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        ItemMeta wandMeta = wand.getItemMeta();
        wandMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        wandMeta.setDisplayName(ColorCoding.changeColor("&c&m:&r &4&lKOTH &f&lRegion Tool &c&m:&r"));
        wandMeta.setLore(Arrays.asList(
                ColorCoding.changeColor("&c&m=-+-=-+-=-+-=-+-=-+-=-+-="),
                ColorCoding.changeColor("&7Use this item to select one"),
                ColorCoding.changeColor("&7position to another position to"),
                ColorCoding.changeColor("&7set the point region for &4&lKOTH."),
                "",
                ColorCoding.changeColor("&c&lADMIN &cTOOL"),
                ColorCoding.changeColor("&8&oNote: Works similarly to World Edit axe"),
                ColorCoding.changeColor("&c&m=-+-=-+-=-+-=-+-=-+-=-+-=")));


        wand.setItemMeta(wandMeta);


       plr.getInventory().setItemInMainHand(wand);



        return false;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        Player plr = event.getPlayer();

        if(event.getAction() == Action.LEFT_CLICK_BLOCK && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BONE && ChatColor.stripColor(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName()).equalsIgnoreCase(": KOTH Region Tool :")){
            if(!plr.hasPermission("koth.admin")){
                return;
            }
            if(!new KothGame(plugin).inProgress) {
                Location location = event.getClickedBlock().getLocation();
                plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("coord1-selected")));
                plugin.getConfig().set("firstlocation.world", location.getWorld().getName());
                plugin.getConfig().set("firstlocation.x", location.getBlockX());
                plugin.getConfig().set("firstlocation.y", location.getBlockY());
                plugin.getConfig().set("firstlocation.z", location.getBlockZ());
                plugin.saveConfig();
                event.setCancelled(true);
            }else{
                plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("setcoords-ingame")));
            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BONE && ChatColor.stripColor(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName()).equalsIgnoreCase(": KOTH Region Tool :")){
            if(!plr.hasPermission("koth.admin")){
                return;
            }
            if(!new KothGame(plugin).inProgress) {
                Location location = event.getClickedBlock().getLocation();
                plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("coord2-selected")));
                plugin.getConfig().set("secondlocation.world", location.getWorld().getName());
                plugin.getConfig().set("secondlocation.x", location.getBlockX());
                plugin.getConfig().set("secondlocation.y", location.getBlockY());
                plugin.getConfig().set("secondlocation.z", location.getBlockZ());
                plugin.saveConfig();
                event.setCancelled(true);
            }else{
                plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("setcoords-ingame")));
            }
        }
    }

}
