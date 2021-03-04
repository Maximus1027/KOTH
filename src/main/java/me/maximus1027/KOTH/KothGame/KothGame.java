package me.maximus1027.KOTH.KothGame;

import com.sun.org.apache.xpath.internal.operations.Bool;
import me.maximus1027.KOTH.Main;
import me.maximus1027.KOTH.Utils.ColorCoding;
import me.maximus1027.KOTH.Utils.HelpPage;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class KothGame implements CommandExecutor {

    public static Main plugin;
    public KothGame(Main plugin) {
        this.plugin = plugin;
    }


    public static boolean inProgress = false;
    Boolean joinToggle = false;


    public static HashMap<Player, Integer> playerPoints = new HashMap();

    public KothGame() {

    }


    public void stopGame(){
        Bukkit.getScheduler().cancelTask(this.KothGame);

        Player lastAlive = (Player) playerPoints.keySet().toArray()[0];
        if(inProgress) {
            Bukkit.broadcastMessage(ColorCoding.changeColor(plugin.getConfig().getString("wongame-message").replaceAll("%player%", lastAlive.getName())));
            for(Player forPlr : Bukkit.getOnlinePlayers()){
                forPlr.sendTitle(ColorCoding.changeColor(plugin.getConfig().getString("koth-wongame-title").replaceAll("%player%", lastAlive.getName())), ColorCoding.changeColor(plugin.getConfig().getString("koth-wongame-subtitle").replaceAll("%player%", lastAlive.getName())));
            }
        }
        lastAlive.setLevel(0);
        playerPoints.clear();

        inProgress = false;

    }


    private Integer maxedPoints = null;




    int KothGame = 0;
    public boolean onCommand(CommandSender sender, final Command command, String s, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }
        final Player plr = (Player) sender;

        if(args.length == 0){
            plr.sendMessage(ChatColor.RED+"KOTH by Maximus1027, xWafless\n'/koth help' for help");
            return false;
        }

        if(args[0].equalsIgnoreCase("join")){
            if(!joinToggle){
                plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("nojoin-message")));
                return false;
            }

            if(playerPoints.containsKey(plr)){
                plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("already-joined")));
                return false;
            }
            if(inProgress){
                plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("alreadystarted-join")));
                return false;
            }
            Bukkit.broadcastMessage(ColorCoding.changeColor(plugin.getConfig().getString("join-message").replaceAll("%player%", plr.getName())));
            playerPoints.put(plr, 0);
            return false;
        }

        if(!plr.hasPermission("koth.admin")){
            plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("no-permissions")));
            return false;
        }


        if(args[0].equalsIgnoreCase("start")){
            plugin.reloadConfig();

            if(plugin.getConfig().getInt("winningscore") <= 0){
                //pointsmax-message
                plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("pointsmax-message")));
                return false;
            }

            if(plugin.getConfig().getInt("firstlocation.x") == 0 || plugin.getConfig().getInt("secondlocation.x") == 0 ){
                //setpoint-message
                plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("coordsmissing-error")));
                return false;
            }

            if(playerPoints.keySet().size() < 2){
                plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("twoplayers-error")));
                return false;
            }

            if(inProgress){
                plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("koth-already-started")));
                return false;
            }

            Location cord1 = new Location(Bukkit.getWorld(plugin.getConfig().getString("firstlocation.world")), plugin.getConfig().getInt("firstlocation.x"), plugin.getConfig().getInt("firstlocation.y"), plugin.getConfig().getInt("firstlocation.z"));
            Location cord2 = new Location(Bukkit.getWorld(plugin.getConfig().getString("secondlocation.world")), plugin.getConfig().getInt("secondlocation.x"), plugin.getConfig().getInt("secondlocation.y"), plugin.getConfig().getInt("secondlocation.z"));

            inProgress = true;

            Bukkit.broadcastMessage(ColorCoding.changeColor(plugin.getConfig().getString("koth-gameannounce")));
            plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("koth-gamestart")));


            

            KothGame = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                public void run() {
                    if(playerPoints.keySet().size() < 2){
                        Bukkit.getScheduler().cancelTask(KothGame);
                       return;
                    }


                for(Player player : playerPoints.keySet()){
                    if(player.getLocation().getX() <= Math.max(cord1.getX(), cord2.getX()) && player.getLocation().getX() >= Math.min(cord1.getX(), cord2.getX())){
                        if(player.getLocation().getZ() <= Math.max(cord1.getZ(), cord2.getZ()) && player.getLocation().getZ() >= Math.min(cord1.getZ(), cord2.getZ())){
                            if(player.getLocation().getY() <= Math.max(cord1.getY(), cord2.getY()) && player.getLocation().getY() >= Math.min(cord1.getY(), cord2.getY())){
                                player.setLevel(playerPoints.get(player)+1);
                                playerPoints.replace(player, player.getLevel());
                                if(playerPoints.get(player) >= plugin.getConfig().getInt("winningscore")){
                                    Bukkit.getScheduler().cancelTask(KothGame);
                                    //Bukkit.broadcastMessage(ChatColor.YELLOW+""+ChatColor.BOLD+player.getName()+" has won the game!");
                                    Bukkit.broadcastMessage(ColorCoding.changeColor(plugin.getConfig().getString("wongame-message").replaceAll("%player%", player.getName())));
                                    for(Player forPlr : Bukkit.getOnlinePlayers()){
                                        forPlr.sendTitle(ColorCoding.changeColor(plugin.getConfig().getString("koth-wongame-title").replaceAll("%player%", player.getName())), ColorCoding.changeColor(plugin.getConfig().getString("koth-wongame-subtitle").replaceAll("%player%", player.getName())));
                                    }

                                    //ColorCoding.changeColor(plugin.getConfig().getString("twoplayers-error"))

                                    for(Player endPlayer : playerPoints.keySet()){
                                        endPlayer.setLevel(0);
                                    }
                                    playerPoints.clear();
                                    inProgress = false;
                                    return;
                                }
                            }
                        }
                    }
                }


                }
            }, 0L, 1L);

        }







        if(args[0].equalsIgnoreCase("help")){
            new HelpPage(plugin).sendHelpPage(plr);
            return false;
        }

        if(args[0].equalsIgnoreCase("jointoggle")){
            if(joinToggle){
                joinToggle = false;
                plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("jointoggle-disable")));
            }else{
                joinToggle = true;
                plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("jointoggle-enable")));
            }
            return false;
        }

        if(args[0].equalsIgnoreCase("stop")){
            Bukkit.getScheduler().cancelTask(KothGame);
            plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("koth-stop")));
            for(Player player : playerPoints.keySet()){
                player.setLevel(0);
            }
            playerPoints.clear();
            inProgress = false;
            return false;
        }

        if(args[0].equalsIgnoreCase("setpoint") && !(args.length < 2)){
            maxedPoints = Integer.valueOf(args[1]);
            plugin.getConfig().set("winningscore", Integer.valueOf(args[1]));
            plugin.saveConfig();
            plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("newpointsmax-message").replaceAll("%newpoints%", args[1])));
            return false;
        }else if(args.length < 2 && args[0].equalsIgnoreCase("setpoint")){
            plr.sendMessage(ColorCoding.changeColor(plugin.getConfig().getString("setpoint-usage")));
            return false;
        }





        return false;
    }
}
