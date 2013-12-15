package com.captainbern.npclib;

import com.captainbern.npclib.npc.NPC;
import com.captainbern.npclib.npc.SlotType;
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
    public void onSpawn(PlayerJoinEvent event) {
        NPC npc = manager.spawnNPC(event.getPlayer().getLocation(), event.getPlayer().getName());
        npc.setInventory(SlotType.ITEM_IN_HAND, new ItemStack(Material.DIAMOND_SWORD));
        npc.setInventory(SlotType.HELMET, new ItemStack(Material.DIAMOND_HELMET));
        npc.setInventory(SlotType.BODY_ARMOR, new ItemStack(Material.DIAMOND_CHESTPLATE));
        npc.setInventory(SlotType.PANTS, new ItemStack(Material.DIAMOND_LEGGINGS));
        npc.setInventory(SlotType.SHOES, new ItemStack(Material.DIAMOND_BOOTS));
    }
}
