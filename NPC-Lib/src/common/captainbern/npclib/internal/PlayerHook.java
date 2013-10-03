package common.captainbern.npclib.internal;

import common.captainbern.npclib.NPCLib;
import common.captainbern.npclib.wrapper.WrappedQueue;
import common.captainbern.reflection.ReflectionUtil;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PlayerHook {
	
	private Queue inboundQueue = new ConcurrentLinkedQueue();
    private final Map<Class<?>, Field> fieldMap = new HashMap<Class<?>, Field>();


    /**
     * While this code needs a lot of optimization though I got it to work (hooray for me! :D )
     * It basically replaces the inboundQueue field in the networkmanager of {@param player} with my
     * WrapperQueue, this wrapperqueue extends the ConcurrentLinkedQueue and just overrides the "add(E)" method
     * so we can start capturing incoming packets. It's a nifty little system but it would never have been possible
     * without the help of Comphenix aka Christian aka aadnk, he, without himself realizing it, teached me a lot
     * about packets and java. Thank you for all the help Comphenix!
     */
	public void hookPlayer(Player player, boolean joining){
		Object nm = getNetworkManager(player);
        Class<?> clazz = nm.getClass();

        WrappedQueue newQueue = new WrappedQueue(player, null);
        Field field = null;
        try {
            for(Field f : clazz.getDeclaredFields()){
                System.out.print("Field=" + f.toString());

                if(f.getName().equals("inboundQueue")){
                    NPCLib.instance.log(ChatColor.GOLD + "Found inboundQueue Field! Yippie Kayee!");
                    f.setAccessible(true);
                    ConcurrentLinkedQueue oldQueue  = (ConcurrentLinkedQueue) f.get(nm);

                    //swap fields
                    Field lock = clazz.getDeclaredField("h");
                    lock.setAccessible(true);
                    lock.get(nm);
                    synchronized (lock){
                        newQueue.addAll(oldQueue);
                        oldQueue.clear();
                    }

                    /**
                     * Replace the old queue by the new queue
                     */
                    f.set(nm, newQueue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Return the NetworkManager of a player
     */
	private Object getNetworkManager(Player player){
        try{
            Object playerConnection = getPlayerConnection(player);
            Object networkmanager = playerConnection.getClass().getField("networkManager").get(playerConnection);
            return networkmanager;
        }catch(Exception e){
            NPCLib.instance.log(ChatColor.RED + "Could not retrieve NetworkManager of player => " + player.getName());
            e.printStackTrace();
            return null;
        }
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
