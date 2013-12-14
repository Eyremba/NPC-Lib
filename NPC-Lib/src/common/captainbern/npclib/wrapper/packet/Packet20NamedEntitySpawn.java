package common.captainbern.npclib.wrapper.packet;

import common.captainbern.npclib.NPCLib;
import common.captainbern.reflection.ReflectionUtil;
import common.captainbern.reflection.packet.DataWatcher;
import common.captainbern.reflection.packet.LazyPacket;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Packet20NamedEntitySpawn extends LazyPacket {

    public Packet20NamedEntitySpawn() {
        super("packet20NamedEntitySpawn");
        DataWatcher datawatcher = new DataWatcher();
        datawatcher.write(0, (Object) (byte) 0);
        datawatcher.write(1, (Object) (short) 0);
        datawatcher.write(8, (Object) (byte) 0);
        super.setPrivateValue("i", datawatcher.getDataWatcherObject());
    }

    public void setEntityID(int id){
        super.setPublicValue("a", id);
    }

    public void setName(String name){
        super.setPublicValue("b", name);
    }

    public void setLocation(Location location){
        super.setPublicValue("c", (int) location.getX() * 32);
        super.setPublicValue("d", (int) location.getY() * 32);
        super.setPublicValue("e", (int) location.getZ() * 32);
        super.setPublicValue("f", getCompressedAngle(location.getYaw()));
        super.setPublicValue("g", getCompressedAngle(location.getPitch()));
    }

    private byte getCompressedAngle(float value) {
        return (byte) (value * 256.0F / 360.0F);
    }

    public void setItemInhand(ItemStack itemStack){
        /**
         * The "getId()" method may be deprecated but the original mc-dev still uses this method.
         * If the method eventually gets removed then I will just create a new enum with all id's.
         */
        super.setPublicValue("h", itemStack == null ? 0 : itemStack.getType().getId());
    }

    private Object convertToNMS(ItemStack itemStack){
        try{
            Object nms_stack = null;

            Class craftstack = ReflectionUtil.getOBCClassExact("inventory.CraftItemStack");
            nms_stack = craftstack.getMethod("asNMSCopy").invoke(craftstack, itemStack);

            return nms_stack;
        }catch(Exception e){
            NPCLib.instance.log(ChatColor.RED + "Could not convert from bukkit to nms itemstack!");
            return null;
        }
    }


}
