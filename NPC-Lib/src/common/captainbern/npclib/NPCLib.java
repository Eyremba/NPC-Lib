package common.captainbern.npclib;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import common.captainbern.npclib.listener.PlayerJoinListener;

public class NPCLib extends JavaPlugin{
	
	public static NPCLib instance;
	
	public boolean usePL = false;
	
	public void onDisable(){
		
	}
	
	public void onEnable(){
		
		instance = this;
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerJoinListener(), this);
		
		//check for protocolLib
		if(pm.isPluginEnabled("ProtocolLib")){
			log("Found ProtocolLib! Using that to hook player connection...");
			usePL = true;
		}
	}
	
	public void log(String message){
		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[NPC-Lib] " + ChatColor.RESET + message);
	}

}
