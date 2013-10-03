package common.captainbern.npclib;

import common.captainbern.npclib.entity.NPC;
import common.captainbern.npclib.internal.PlayerHook;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import common.captainbern.npclib.internal.ProtocolLibHook;
import common.captainbern.npclib.listener.PlayerListener;

import java.util.LinkedList;

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
			log(ChatColor.GREEN + "Found ProtocolLib! Using that to listen for incoming packets...");
			usePL = true;
			(new ProtocolLibHook()).setUpInteractListener();
		}

        if(!usePL){
            PlayerHook hp = new PlayerHook();
            for(Player player : Bukkit.getOnlinePlayers()){
                hp.hookPlayer(player, true);
            }
        }

	}

    private class NPCUpdater implements  Runnable{

        LinkedList<NPC> update_queue = new LinkedList<NPC>();

        public void queuer(NPC npc){
            if(!update_queue.contains(npc)){
                update_queue.add(npc);
            }else{
                throw new RuntimeException("Cannot queue already queued NPC => {" + npc.getName() + "}");
            }
        }

        @Override
        public void run() {
            for(NPC npc : update_queue){
                //npc.update();
                /*
                update player connection
                 */
            }
        }
    }
	
	public NPCManager getNPCManager(){
		return npcmanager;
	}
	
	public void log(String message){
		Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[NPC-Lib] " + ChatColor.RESET + message);
	}

}
