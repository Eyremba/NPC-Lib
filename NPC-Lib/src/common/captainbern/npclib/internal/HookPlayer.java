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
		
	}
	
	private Object getNetworkManager(Player player){
		return null;
	}
	
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
