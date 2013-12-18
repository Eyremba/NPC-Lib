package com.captainbern.npclib;

import com.captainbern.npclib.events.PlayerInteractNPCEvent;
import com.captainbern.npclib.npc.NPC;
import com.captainbern.npclib.npc.SlotType;
import com.captainbern.npclib.utils.Action;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * An example class of how this lib can be used.
 */
public class Test extends JavaPlugin implements Listener {

    NPCManager manager;

    @Override
    public void onEnable() {
        //manager = new NPCManager(this);

        //Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        manager.shutdown();
    }

    @EventHandler
    public void onSpawn(PlayerJoinEvent event) {
        NPC npc = manager.spawnNPC(event.getPlayer().getLocation(), event.getPlayer().getName());
    }

    @EventHandler
    public void onInteract(PlayerInteractNPCEvent event) {
        NPC npc = event.getNPC();
        Player player = event.getPlayer();

        if(event.getAction() == Action.LEFT_CLICK) {
            npc.setBlocking(!npc.isBlocking());
        }

        if(event.getAction() == Action.RIGHT_CLICK) {
            npc.setInventory(getTypeFor(player.getItemInHand()), player.getItemInHand());
        }
    }

    public static SlotType getTypeFor(ItemStack stack) {
        List<Material> HELMETS = Lists.newArrayList();
        HELMETS.add(Material.LEATHER_HELMET);
        HELMETS.add(Material.CHAINMAIL_HELMET);
        HELMETS.add(Material.IRON_HELMET);
        HELMETS.add(Material.GOLD_HELMET);
        HELMETS.add(Material.DIAMOND_HELMET);

        List<Material> CHESTPLATES = Lists.newArrayList();
        CHESTPLATES.add(Material.LEATHER_CHESTPLATE);
        CHESTPLATES.add(Material.CHAINMAIL_CHESTPLATE);
        CHESTPLATES.add(Material.IRON_CHESTPLATE);
        CHESTPLATES.add(Material.GOLD_CHESTPLATE);
        CHESTPLATES.add(Material.DIAMOND_CHESTPLATE);

        List<Material> PANTS = Lists.newArrayList();
        PANTS.add(Material.LEATHER_LEGGINGS);
        PANTS.add(Material.CHAINMAIL_LEGGINGS);
        PANTS.add(Material.IRON_LEGGINGS);
        PANTS.add(Material.GOLD_LEGGINGS);
        PANTS.add(Material.DIAMOND_LEGGINGS);

        List<Material> SHOES = Lists.newArrayList();
        SHOES.add(Material.LEATHER_BOOTS);
        SHOES.add(Material.CHAINMAIL_BOOTS);
        SHOES.add(Material.IRON_BOOTS);
        SHOES.add(Material.GOLD_BOOTS);
        SHOES.add(Material.DIAMOND_BOOTS);

        Material material = stack.getType();

        if(HELMETS.contains(material)) {
            return SlotType.HELMET;
        }
        if(CHESTPLATES.contains(material)) {
            return SlotType.BODY_ARMOR;
        }
        if(PANTS.contains(material)) {
            return SlotType.PANTS;
        }
        if(SHOES.contains(material)) {
            return SlotType.SHOES;
        }
        return SlotType.ITEM_IN_HAND;
    }
}
