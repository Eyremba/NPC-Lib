package common.captainbern.npclib;

import common.captainbern.npclib.internal.PacketInterceptor;
import common.captainbern.npclib.internal.PlayerHook;

import common.captainbern.npclib.listener.PacketHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import common.captainbern.npclib.internal.ProtocolLibHook;
import common.captainbern.npclib.listener.PlayerListener;

public class NPCLib extends JavaPlugin{
	
	public static NPCLib instance;
	private NPCManager npcmanager;
    private PlayerHook playerHook;
    private PacketHandler packetHandler;
    private boolean usingProtocolLib = false;
	
	public void onDisable(){
		npcmanager.despawnAll();
	}
	
	public void onEnable(){
		
		instance = this;
		npcmanager = new NPCManager();

        PluginManager pm = Bukkit.getPluginManager();

		/**
		 * Check for ProtocolLib, if it is present then use that.
		 */
		if(pm.isPluginEnabled("ProtocolLib")){
			log(ChatColor.GREEN + "Found ProtocolLib! Using that to listen for incoming packets...");
			usingProtocolLib = true;
            (new ProtocolLibHook()).setUpInteractListener();
		}

        if(!usingProtocolLib){
            log(ChatColor.GREEN + "ProtocolLib not detected! So instead we will be using our own methods to listen for incoming packets...");
            packetHandler = new PacketHandler();
           /* playerHook = new PlayerHook();
            for(Player player : Bukkit.getOnlinePlayers()){
                playerHook.hookPlayer(player, true);
            }*/
            new PacketInterceptor(this);
            //register listeners
            pm.registerEvents(new PlayerListener(), this);
        }
	}
	
	public NPCManager getNPCManager(){
		return npcmanager;
	}

    public PlayerHook getPlayerHook(){
        return playerHook;
    }

    public PacketHandler getPacketHandler(){
        return packetHandler;
    }
	
	public void log(String message){
		Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[NPC-Lib] " + ChatColor.RESET + message);
	}

}
