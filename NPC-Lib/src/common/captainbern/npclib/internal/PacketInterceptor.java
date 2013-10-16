/**
 * This class is used to intercept incoming packets without the use of protocolLib.
 * I wrote my own version but then saw a version made by Comphenix and decided to update
 * mine a bit so it works much more efficient. (it's based of Comphenix' one)
 */
package common.captainbern.npclib.internal;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import common.captainbern.npclib.NPCLib;
import common.captainbern.reflection.ReflectionUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

import java.util.concurrent.ConcurrentLinkedQueue;

public class PacketInterceptor implements Listener{

    private Plugin plugin;

    private Object networkManager = null;
    private Field inboundQueueField = null;

    private Multimap<Player, PlayerFieldData> swapped = ArrayListMultimap.create();

    private static class PlayerFieldData {

        private final Field field;
        private final Object target;
        private  final Object value;

        //used to restore the networkmanager.
        public PlayerFieldData(Field field, Object target, Object value){
            this.field = field;
            this.target = target;
            this.value = value;
        }

        public void rollback(){
            try{
                field.set(target, value);
            }catch(Exception e){
                throw new RuntimeException("Unable to reset the networkmanager" + e);
            }
        }
    }

    public void interceptPlayer(Player player) throws Exception{
        if(swapped.containsKey(player)){
            throw new RuntimeException("Can not inject " + player + " because he is already.");
        }
        if(networkManager == null)
            networkManager = getNetworkManager(player);

        if(inboundQueueField == null)
            inboundQueueField = ReflectionUtil.getDeclaredField("inboundQueue", networkManager.getClass());
        inboundQueueField.setAccessible(true);
        if(!inboundQueueField.get(networkManager).getClass().getSimpleName().equals("ConcurrentLinkedQueue")){
            throw new RuntimeException("Seems like another plugin is messing around with the networkmanager/packets! Please install ProtocolLib!");
        }

        ConcurrentLinkedQueue inboundQueue = (ConcurrentLinkedQueue) inboundQueueField.get(networkManager);

        //save swap
        swapped.put(player, new PlayerFieldData(inboundQueueField, networkManager, inboundQueue));

        //proxy dem!
        inboundQueueField.set(networkManager, new ProxyQueue(player));
    }

    public void unInterceptPlayer(Player player){
        for(PlayerFieldData data : swapped.removeAll(player)){
            data.rollback();
        }
    }

    public PacketInterceptor(Plugin plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);

        for(Player player : Bukkit.getOnlinePlayers()){
            try{
                interceptPlayer(player);
            }catch(Exception e){
                throw new RuntimeException("Could not inject " + player + e);
            }
        }
    }

    public void shutdownService(){
        HandlerList.unregisterAll(this);

        for(PlayerFieldData data : swapped.values()){
            data.rollback();
        }
        swapped.clear();
    }

    @EventHandler
    public void onJoin(final PlayerLoginEvent event){
        try{
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    try {
                        interceptPlayer(event.getPlayer());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch(Exception e){
            throw new RuntimeException("Could not inject " + event.getPlayer() + e);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        unInterceptPlayer(event.getPlayer());
    }

    private Object getNetworkManager(Player player){
        try{
            Object playerConnection = getPlayerConnection(player);
            Object networkmanager = playerConnection.getClass().getField("networkManager").get(playerConnection);
            return networkmanager;
        }catch(Exception e){
            NPCLib.instance.log(ChatColor.RED + "Could not retrieve NetworkManager of " + player);
            e.printStackTrace();
            return null;
        }
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

    private class ProxyQueue<E> extends ConcurrentLinkedQueue<E>{

        private Player owner;

        public ProxyQueue(Player player){
            this.owner = player;
        }

        @Override
        public boolean add(E e){
            System.out.print(e.getClass().getSimpleName());
            return super.add(e);
        }
    }
}
