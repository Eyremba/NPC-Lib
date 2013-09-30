package common.captainbern.npclib;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import common.captainbern.npclib.listener.PlayerJoinListener;

public class NPCLib extends JavaPlugin{
	
	public static NPCLib instance;
	private NPCManager npcmanager;
	
	public boolean usePL = false;
	
	public void onDisable(){
		
	}
	
	public void onEnable(){
		
		instance = this;
		npcmanager = new NPCManager();
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerJoinListener(), this);
		
		/**
		 * Check for ProtocolLib, if it is present then use that; else use my own fancy packet listener/writer
		 */
		if(pm.isPluginEnabled("ProtocolLib")){
			log("Found ProtocolLib! Using that to hook player connection...");
			usePL = true;
		}
	}
	
	public NPCManager getNPCManager(){
		return npcmanager;
	}
	
	public void log(String message){
		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[NPC-Lib] " + ChatColor.RESET + message);
	}

}
