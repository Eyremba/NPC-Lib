/* 
 * This is the main class, at the moment you are able to create basic NPC's.
 * The only problem I'm facing now is that an npc will not be shown to a player that dies/just joined.
 * (because the npc-packet doesn't get transmitted, now I need to find a good way to do this without eating
 * all the server resources)
 */
package common.captainbern.npclib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import common.captainbern.npclib.entity.NPC;

public class NPCManager {
	
	private HashMap<String, NPC> npc_by_name = new HashMap<String, NPC>();
	private List<NPC> npcs = new ArrayList<NPC>();
	
	private int id = 123456789;
	
	public NPCManager(){
		
	}

	public NPC createNpc(String name, Location location){
		if(npc_by_name.containsKey(name)){
			Bukkit.getLogger().warning("There already exists an NPC with the name: " + name + "!");
			return null;
		}
		int entityid = id += 1;
		
		NPC npc = new NPC(name, location);
		npc.setId(entityid);
		npc.update();
		
		npcs.add(npc);
		npc_by_name.put(npc.getName(), npc);
		
		return npc;
	}
	
	public NPC getNpcByName(String name){
		if(npc_by_name.containsKey(name)){
			return npc_by_name.get(name);
		}else{
			return null;
		}
	}
}
