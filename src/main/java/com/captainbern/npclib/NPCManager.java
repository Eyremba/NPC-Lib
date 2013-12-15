package com.captainbern.npclib;

import com.captainbern.npclib.injector.PlayerInjector;
import com.captainbern.npclib.npc.EntityHuman;
import com.captainbern.npclib.npc.NPC;
import com.captainbern.npclib.utils.PacketFactory;
import com.captainbern.npclib.utils.PlayerUtils;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

public class NPCManager implements Listener{

    private static NPCManager INSTANCE;

    public static final ModuleLogger LOGGER = new ModuleLogger("NPC");
    public static final ModuleLogger LOGGER_REFLECTION = LOGGER.getModule("Reflection");

    private BiMap<Integer, EntityHuman> LOOKUP = HashBiMap.create();

    public NPCManager(Plugin plugin) {
        startUp();
        Bukkit.getPluginManager().registerEvents(this, plugin);
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

    public void startUp() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            PlayerInjector.injectPlayer(player);
        }
    }

    public void shutdown() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            PlayerInjector.uninjectPlayer(player);
        }
    }

    private void updatePlayer(Player player) {
        for(NPC npc : LOOKUP.values()) {
            if(npc.getLocation().getWorld().equals(player.getWorld())) {
                PlayerUtils.sendPacket(player, PacketFactory.craftSpawnPacket(npc));

                if(npc.isSleeping()) {
                    PlayerUtils.sendPacket(player, PacketFactory.craftSleepPacket(npc));
                }

                if(!PacketFactory.craftEquipmentPacket(npc).isEmpty()) {
                    for(Object packet : PacketFactory.craftEquipmentPacket(npc)) {
                        PlayerUtils.sendPacket(player, packet);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PlayerInjector.injectPlayer(event.getPlayer());

        updatePlayer(event.getPlayer());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        updatePlayer(event.getPlayer());
    }
}
