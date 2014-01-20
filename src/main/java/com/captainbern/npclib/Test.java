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
 * An example class of how this lib can be used. This example will spawn
 * a Human npc at the location where a player spawns.
 *
 * When left clicking the npc it will block/stop blocking and when right clicking
 * it will define if the item is a wearable armor item, if so then it will wear it,
 * if not then it will just hold the item in hand.
 */
public class Test extends JavaPlugin implements Listener {

    NPCManager manager;

    @Override
    public void onEnable() {
        manager = NPCManager.getNPCManager(this);

        Bukkit.getPluginManager().registerEvents(this, this);
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

    static List<Material> HELMETS = Lists.newArrayList();
    static List<Material> CHESTPLATES = Lists.newArrayList();
    static List<Material> PANTS = Lists.newArrayList();
    static List<Material> SHOES = Lists.newArrayList();

    {
        HELMETS.add(Material.LEATHER_HELMET);
        HELMETS.add(Material.CHAINMAIL_HELMET);
        HELMETS.add(Material.IRON_HELMET);
        HELMETS.add(Material.GOLD_HELMET);
        HELMETS.add(Material.DIAMOND_HELMET);

        CHESTPLATES.add(Material.LEATHER_CHESTPLATE);
        CHESTPLATES.add(Material.CHAINMAIL_CHESTPLATE);
        CHESTPLATES.add(Material.IRON_CHESTPLATE);
        CHESTPLATES.add(Material.GOLD_CHESTPLATE);
        CHESTPLATES.add(Material.DIAMOND_CHESTPLATE);


        PANTS.add(Material.LEATHER_LEGGINGS);
        PANTS.add(Material.CHAINMAIL_LEGGINGS);
        PANTS.add(Material.IRON_LEGGINGS);
        PANTS.add(Material.GOLD_LEGGINGS);
        PANTS.add(Material.DIAMOND_LEGGINGS);


        SHOES.add(Material.LEATHER_BOOTS);
        SHOES.add(Material.CHAINMAIL_BOOTS);
        SHOES.add(Material.IRON_BOOTS);
        SHOES.add(Material.GOLD_BOOTS);
        SHOES.add(Material.DIAMOND_BOOTS);
    }

    public static SlotType getTypeFor(ItemStack stack) {


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
