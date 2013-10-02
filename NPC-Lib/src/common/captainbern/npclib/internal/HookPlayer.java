package common.captainbern.npclib.internal;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import common.captainbern.npclib.NPCLib;
import common.captainbern.reflection.ReflectionUtil;

public class HookPlayer {
	
	private Queue inboundQueue = new ConcurrentLinkedQueue();
	
	public void hookPlayer(Player player){
		Object networkmanager = getNetworkManager(player);
        Class<?> clazz = networkmanager.getClass();

        /* swap "inboundQueue" field , Not sure if this method will work to listen for incoming packets
         * but in theory it should work...
         */
	}

    /**
     * Return the NetworkManager of a player
     */
	private Object getNetworkManager(Player player){
        try{
            Object playerConnection = getPlayerConnection(player);
            Object networkmanager = playerConnection.getClass().getField("networkManager").get(playerConnection);
        }catch(Exception e){
            NPCLib.instance.log(ChatColor.RED + "Could not retrieve NetworkManager of player => " + player.getName());
            e.printStackTrace();
        }
		return null;
	}

    /**
     * Returns the PlayerConnection of a player
     */
	private Object getPlayerConnection(Player player){
		try{
		Object nms = playerToNMS(player);
		Object playerConnection = nms.getClass().getField("playerConnection").get(nms);
		return playerConnection;
		}catch(Exception e){
			NPCLib.instance.log(ChatColor.RED + "Could not retrieve player: " + player.getName() + "'s playerConnection!");
			return null;
		}
	}

    /**
     * Used to convert a bukkit player to EntityPlayer
     */
	private Object playerToNMS(Player player){
		Object entityPlayer = null;
		try{
		entityPlayer = ReflectionUtil.getMethod("getHandle", player.getClass(), 0).invoke(player);
		}catch(Exception e){
			NPCLib.instance.log(ChatColor.RED + "Could not convert player: " + player.getName() + " from bukkit to NMS Entity!");
			return null;
		}
		return entityPlayer;
	}
}
