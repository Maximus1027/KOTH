package me.maximus1027.KOTH.Utils;

import me.maximus1027.KOTH.KothGame.KothGame;
import org.bukkit.entity.Player;

public class PlayerUtils {
    public static boolean PlayerInGame(Player plr){
        if(KothGame.playerPoints.containsKey(plr)){
            return true;
        }else{
            return false;
        }
    }
}
