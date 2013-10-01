package common.captainbern.npclib;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import common.captainbern.npclib.internal.ProtocolLibHook;
import common.captainbern.npclib.listener.PlayerListener;

public class NPCLib extends JavaPlugin{
	
	public static NPCLib instance;
	private NPCManager npcmanager;
	
	public boolean usePL = false;
	
	public void onDisable(){
		npcmanager.despawnAll();
	}
	
	public void onEnable(){
		
		instance = this;
		npcmanager = new NPCManager();
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerListener(), this);
		
		/**
		 * Check for ProtocolLib, if it is present then use that.
		 */
		if(pm.isPluginEnabled("ProtocolLib")){
			log(ChatColor.GREEN + "Found ProtocolLib! Using that to listen for incomming packets...");
			usePL = true;
			(new ProtocolLibHook()).setUpInteractListener();
		}
	}
	
	public NPCManager getNPCManager(){
		return npcmanager;
	}
	
	public void log(String message){
		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[NPC-Lib] " + ChatColor.RESET + message);
	}

}
