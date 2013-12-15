package com.captainbern.npclib;

import com.captainbern.npclib.events.PlayerInteractNPCEvent;
import com.captainbern.npclib.npc.NPC;
import com.captainbern.npclib.npc.SlotType;
import com.captainbern.npclib.utils.Action;
import com.captainbern.npclib.utils.PacketFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Test extends JavaPlugin implements Listener {

    NPCManager manager;

    @Override
    public void onEnable() {
        manager = new NPCManager(this);

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        manager.shutdown();
    }

    @EventHandler
    public void onSpawn(final PlayerJoinEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                NPC npc = manager.spawnNPC(event.getPlayer().getLocation(), event.getPlayer().getName());

            }
        }, 20L);
    }

    @EventHandler
    public void onInteract(PlayerInteractNPCEvent event) {
        if(event.getAction() == Action.LEFT_CLICK) {
            event.getNPC().hurt();
        }

        if(event.getAction() == Action.RIGHT_CLICK) {
            manager.updateNPC(event.getNPC(), PacketFactory.craftSleepPacket(event.getNPC()));
        }
    }
}
