/* 
 * This is the main class, at the moment you are able to create basic NPC's.
 * The only problem I'm facing now is that an npc will not be shown to a player that dies/just joined.
 * (because the npc-packet doesn't get transmitted, now I need to find a good way to do this without eating
 * all the server resources)
 */
package common.captainbern.npclib;

import common.captainbern.npclib.entity.NPC;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class NPCManager {

    /**
     * We will use a LinkedList instead of an ArrayList since a LinkedList is faster(- lol No) and can't contain 2 times the
     * same value. (May change to arraylist in the future, who knows?)
     */
    private LinkedList<NPC> npcs = new LinkedList<NPC>();

    private ConcurrentHashMap<Integer, NPC> npcIDS = new ConcurrentHashMap<Integer, NPC>();
    private ConcurrentHashMap<String, NPC> npcNAMES = new ConcurrentHashMap<String, NPC>();

    private Object lock;

    public NPCManager(){
        lock = new Object();
        synchronized (lock){
            // more code here
        }
    }

    public NPC createNpc(String name, Location location){
        if(npcNAMES.containsKey(name)){
            NPCLib.instance.log(ChatColor.RED + "There already exists an NPC with the name: " + name + "!");
            return null;
        }

        int id = getNextID();

        NPC npc = new NPC(name, location);
        npc.setId(id);
        npc.update();

        npcs.add(npc);

        npcNAMES.put(npc.getName(), npc);
        npcIDS.put(id, npc);

        return npc;
    }

    public NPC createNpc(String name, Location location, int id){
        if(npcIDS.contains(id)){
            NPCLib.instance.log(ChatColor.RED + "There already exists an NPC with that id, we will return that NPC instead! (" + id + ")");
            return  getNpcById(id);
        } else {
            if(npcNAMES.containsKey(name)){
                NPCLib.instance.log(ChatColor.RED + "There already exists an NPC with the name: " + name + "!");
                return null;
            }

            NPC npc = new NPC(name, location);
            npc.setId(id);
            npc.update();

            npcs.add(npc);

            npcNAMES.put(npc.getName(), npc);
            npcIDS.put(id, npc);

            return npc;
        }
    }

    protected int nextID = Integer.MIN_VALUE;
    private int getNextID(){
        return nextID++;
    }

    public NPC getNpcByName(String name){
        if(npcNAMES.containsKey(name)){
            return npcNAMES.get(name);
        }else{
            return null;
        }
    }

    public NPC getNpcById(int id){
        if(npcIDS.containsKey(id)){
            return npcIDS.get(id);
        }else{
            return null;
        }
    }

    protected void despawnAll() {
        for(NPC npc : npcs){
            npcIDS.remove(npc.getId());
            npcNAMES.remove(npc.getName());
            npc.destroy();
        }
    }
}
