/**
 * This class is used to intercept incoming packets without the use of protocolLib.
 * I wrote my own version but then saw a version made by Comphenix and decided to update
 * mine a bit so it works much more efficient. (it's based of Comphenix' one)
 */
package common.captainbern.npclib.internal;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ForwardingQueue;
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

import javax.annotation.Nonnull;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PacketInterceptor implements Listener{

    private Plugin plugin;

    private Object networkManager = null;
    private Field inboundQueueField = null;

    private Multimap<Player, Swapper> swapped = ArrayListMultimap.create();

    private void interceptPlayer(Player player) throws Exception{

        if(swapped.containsKey(player)){
            throw new RuntimeException("Can not inject " + player + " because he is already.");
        }
        if(networkManager == null)
            networkManager = getNetworkManager(player);

        if(inboundQueueField == null)
            inboundQueueField = ReflectionUtil.getDeclaredField("inboundQueue", networkManager.getClass());
        inboundQueueField.setAccessible(true);

        Field lockField = ReflectionUtil.getDeclaredField("h", networkManager.getClass());
        lockField.setAccessible(true);
        Object lock = lockField.get(networkManager);

        ConcurrentLinkedQueue oldQueue = (ConcurrentLinkedQueue) inboundQueueField.get(networkManager);
        ProxyQueue proxy = new ProxyQueue(player, oldQueue);

        synchronized (lock){
            proxy.addAll(oldQueue); // Making sure older packets also get processed.
        }

        swapped.put(player, new Swapper(inboundQueueField, networkManager, proxy).swap());

    }

    private void unInterceptPlayer(Player player){
        for(Swapper swap : swapped.removeAll(player)){
            swap.swap();
        }
    }

    public PacketInterceptor(Plugin plugin){
        if(!NPCLib.instance.getServer().getName().equalsIgnoreCase("CraftBukkit")){
            throw new UnsupportedOperationException("We can not detect packets with this server-mod! Please install ProtocolLib!");
        }

        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);

        for(Player player : Bukkit.getOnlinePlayers()){
            try{
                interceptPlayer(player);
            }catch(Exception e){
                throw new RuntimeException("Could not inject " + player + e + "\n Please install ProtocolLib!");
            }
        }
    }

    public void shutdownService(){
        HandlerList.unregisterAll(this);

        for(Swapper swap : swapped.values()){
            swap.swap();
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
            }, 1L);
        }catch(Exception e){
            throw new RuntimeException("Could not inject " + event.getPlayer() + e);
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
            @Override
            public void run() {
                for(Swapper swap : swapped.values()){
                    try{
                        if(swap.getCurrentValue() != swap.getOldValue()){
                            plugin.getLogger().warning("Seems like there is another plugin trying to intercept or block packets.\n Please install ProtocolLib.");
                        }
                    }catch(IllegalAccessException e){
                        e.printStackTrace();
                    }
                }
            }
        },20L);
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

    public void processPacket(@Nonnull Object e, Player owner){
          //process packet here...
    }

    private static class Swapper{

        private Field inboundQueueField;
        private Object networkManager;
        private Object newValue;

        private WeakReference<Object> oldValue;

        public Swapper(Field field, Object manager, Object newValue) throws IllegalAccessException{
            this.inboundQueueField = field;
            this.networkManager = manager;
            this.newValue = newValue;

            oldValue = new WeakReference<Object>(getCurrentValue());
        }

        private Object getOldValue() throws IllegalAccessException{
            return oldValue.get();
        }

        public Object getCurrentValue() throws IllegalAccessException{
            return inboundQueueField.get(networkManager);
        }

        public Swapper swap(){
            try{
                inboundQueueField.set(networkManager, newValue);
                return new Swapper(inboundQueueField, networkManager, getOldValue());
            }catch(IllegalAccessException e){
                throw new RuntimeException("Could not access/swap field" + e);
            }
        }
    }

    private class ProxyQueue extends ForwardingQueue<Object>{

        private Player owner;
        private ConcurrentLinkedQueue rootQueue;

        public ProxyQueue(Player owner, ConcurrentLinkedQueue rootQueue){
            this.owner = owner;
            this.rootQueue = rootQueue;
        }

        public Player getOwner(){
            return owner;
        }

        @Override
        public boolean add(Object e){
            processPacket(e, owner);
            return super.add(e);
        }

        @Override
        protected Queue<Object> delegate() {
            return rootQueue;
        }
    }
}
