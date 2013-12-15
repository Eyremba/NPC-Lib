package com.captainbern.npclib;

import com.captainbern.npclib.injector.PlayerInjector;
import com.captainbern.npclib.npc.EntityHuman;
import com.captainbern.npclib.npc.NPC;
import com.captainbern.npclib.utils.PacketFactory;
import com.captainbern.npclib.utils.PlayerUtil;
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
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

public class NPCManager implements Listener{

    private static NPCManager INSTANCE;

    private final Plugin HANDLER;

    public static final ModuleLogger LOGGER = new ModuleLogger("NPC");
    public static final ModuleLogger LOGGER_REFLECTION = LOGGER.getModule("Reflection");

    private BiMap<Integer, EntityHuman> LOOKUP = HashBiMap.create();

    public NPCManager(Plugin plugin) {
        startUp();
        Bukkit.getPluginManager().registerEvents(this, plugin);
        INSTANCE = this;
        HANDLER = plugin;
    }

    public NPC spawnNPC(Location location, String name) {
        int id = getNextID();
        EntityHuman human = new EntityHuman(location, name, id);
        LOOKUP.put(id, human);

        updateNPC(human);
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
                PlayerUtil.sendPacket(player, PacketFactory.craftSpawnPacket(npc));

                if(!PacketFactory.craftEquipmentPacket(npc).isEmpty()) {
                    for(Object packet : PacketFactory.craftEquipmentPacket(npc)) {
                        PlayerUtil.sendPacket(player, packet);
                    }
                }
            }
        }
    }

    public void updateNPC(NPC npc) {
        updateNPC(npc, PacketFactory.craftSpawnPacket(npc));

        for(Object packet : PacketFactory.craftEquipmentPacket(npc)) {
            updateNPC(npc, packet);
        }
    }

    public void updateNPC(NPC npc, Object packet) {
        if(packet == null)
            return;

        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.getWorld().equals(npc.getLocation().getWorld())) {
                PlayerUtil.sendPacket(player, packet);
            }
        }
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(HANDLER, new Runnable() {
            @Override
            public void run() {
                PlayerInjector.injectPlayer(event.getPlayer());

                updatePlayer(event.getPlayer());
            }
        }, 1L); //one tick so the player object is initialized properly.
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        updatePlayer(event.getPlayer());
    }
}
