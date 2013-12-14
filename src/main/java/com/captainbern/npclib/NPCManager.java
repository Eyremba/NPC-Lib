package com.captainbern.npclib;

import com.captainbern.npclib.npc.EntityHuman;
import com.captainbern.npclib.npc.NPC;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import sun.plugin2.main.server.Plugin;

public class NPCManager {

    private static NPCManager INSTANCE;

    public static final ModuleLogger LOGGER = new ModuleLogger("NPC");
    public static final ModuleLogger LOGGER_REFLECTION = LOGGER.getModule("Reflection");

    private BiMap<Integer, EntityHuman> LOOKUP = HashBiMap.create();

    public NPCManager(Plugin plugin) {
        INSTANCE = this;
    }

    public NPC spawnNPC(Location location, String name) {
        int id = getNextID();
        EntityHuman human = new EntityHuman(location, name, id);
        LOOKUP.put(id, human);
        return human;
    }

    public boolean isNPC(int id) {
        return LOOKUP.containsKey(id) ? true : false;
    }

    public NPC getNPC(int id) {
        if(isNPC(id)) {
            return LOOKUP.get(id);
        }
        LOGGER.warning("Failed to return NPC with id: " + id);
        return null;
    }

    int nextID = Integer.MIN_VALUE;
    protected int getNextID() {
        int id =+ nextID++;
        for(World world : Bukkit.getWorlds()) {
            for(Entity entity : world.getEntities()) {
                if (entity.getEntityId() != id) {
                    return id;
                } else {
                    return getNextID();
                }
            }
        }
        return id;
    }

    public static NPCManager getInstance() {
        if(INSTANCE == null) {
            LOGGER.warning("INSTANCE is NULL!");
            return null;
        }
        return INSTANCE;
    }
}
