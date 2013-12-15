package com.captainbern.npclib.npc;

import com.captainbern.npclib.NPCManager;
import com.captainbern.npclib.utils.PacketFactory;
import com.captainbern.npclib.wrappers.DataWatcher;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class EntityHuman implements NPC {

    private final int id;
    private GameProfile profile;
    private Location location;
    private ItemStack itemInHand;
    private ItemStack helmet;
    private ItemStack body;
    private ItemStack pants;
    private ItemStack shoes;
    private boolean sleeping;

    private DataWatcher dataWatcher;

    public EntityHuman(Location location, String name, int id) {
        this.location = location;
        this.id = id;
        this.profile = new GameProfile("NPC", name);
        this.itemInHand = new ItemStack(Material.AIR);

        this.dataWatcher = new DataWatcher();
        dataWatcher.write(0, (Object) (byte) 0);
        dataWatcher.write(1, (Object) (short) 0);
        dataWatcher.write(8, (Object) (byte) 0);
    }

    @Override
    public int getEntityID() {
        return id;
    }

    @Override
    public String getName() {
        return profile.getName();
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public ItemStack getInventory(SlotType type) {
        switch (type) {
            case ITEM_IN_HAND : return this.itemInHand;
            case HELMET : return this.helmet;
            case BODY_ARMOR : return this.body;
            case PANTS : return this.pants;
            case SHOES : return this.shoes;
            default : return null;
        }
    }

    @Override
    public void setName(String name) {
        profile = new GameProfile("NPC", name);
        NPCManager.getInstance().updateNPC(this);
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
        NPCManager.getInstance().updateNPC(this);
    }

    @Override
    public void setInventory(SlotType type, ItemStack item) {
        switch (type) {
            case ITEM_IN_HAND : this.itemInHand = item;
                break;
            case HELMET : this.helmet = item;
                break;
            case BODY_ARMOR : this.body = item;
                break;
            case PANTS : this.pants = item;
                break;
            case SHOES : this.shoes = item;
                break;
        }
        NPCManager.getInstance().updateNPC(this);
    }

    @Override
    public boolean isSleeping() {
        return sleeping;
    }

    @Override
    public void setSleeping(boolean sleeping) {
        this.sleeping = sleeping;
        if(sleeping) {
            NPCManager.getInstance().updateNPC(this, PacketFactory.craftSleepPacket(this));
        } else {
            NPCManager.getInstance().updateNPC(this, PacketFactory.craftLeaveBedPacket(this));
        }
    }

    @Override
    public void setDataWatcher(DataWatcher dataWatcher) {
        this.dataWatcher = dataWatcher;
        NPCManager.getInstance().updateNPC(this);
    }

    @Override
    public DataWatcher getDataWatcher() {
        return this.dataWatcher;
    }

    @Override
    public GameProfile getGameProfile() {
        return profile;
    }

    @Override
    public void hurt() {
        NPCManager.getInstance().updateNPC(this, PacketFactory.craftHurtPacket(this));
    }

    @Override
    public void swingArm() {
        NPCManager.getInstance().updateNPC(this, PacketFactory.craftArmSwingPacket(this));
    }
}
