package com.captainbern.npclib.npc;

import com.captainbern.npclib.wrappers.DataWatcher;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class EntityHuman implements NPC {

    private final int id;
    private GameProfile profile;
    private Location location;
    private double health;
    private ItemStack itemInHand;
    private ItemStack helmet;
    private ItemStack body;
    private ItemStack pants;
    private ItemStack shoes;

    private DataWatcher dataWatcher;

    public EntityHuman(Location location, String name, int id) {
        this.location = location;
        this.id = id;
        this.profile = new GameProfile("NPC", name);
        this.health = 20.0;
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
    public double getHealth() {
        return health;
    }

    @Override
    public void setName(String name) {
        profile = new GameProfile("NPC", name);
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
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
    }

    @Override
    public void setHealth(double health) {
        this.health = health;
    }

    @Override
    public void hurt() {

    }

    @Override
    public void sleep() {

    }

    @Override
    public void setDataWatcher(DataWatcher dataWatcher) {
        this.dataWatcher = dataWatcher;
    }

    @Override
    public DataWatcher getDataWatcher() {
        return this.dataWatcher;
    }

    @Override
    public GameProfile getGameProfile() {
        return profile;
    }
}
