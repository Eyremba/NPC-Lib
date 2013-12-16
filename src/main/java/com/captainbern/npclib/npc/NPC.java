package com.captainbern.npclib.npc;

import com.captainbern.npclib.wrappers.DataWatcher;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public interface NPC {

    public int getEntityID();

    public String getName();

    public Location getLocation();

    public ItemStack getInventory(SlotType type);

    public void setName(String name);

    public void setLocation(Location location);

    public void setInventory(SlotType type, ItemStack item);

    public void setSleeping(boolean sleeping);

    public boolean isSleeping();

    public void setDataWatcher(DataWatcher dataWatcher);

    public DataWatcher getDataWatcher();

    public GameProfile getGameProfile();

    public void hurt();

    public void swingArm();

    public void setBlocking(boolean blocking);

    public boolean isBlocking();

    public void setCrouched(boolean crouched);

    public boolean isCrouching();

    public void despawn();

    public void lookAt(Location location);

    public void reset();

}
