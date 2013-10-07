package common.captainbern.npclib.wrapper.packet;

import common.captainbern.npclib.NPCLib;
import common.captainbern.npclib.entity.NPC;
import common.captainbern.reflection.ReflectionUtil;
import common.captainbern.reflection.packet.LazyPacket;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class Packet5EntityEquipment extends LazyPacket {

    public Packet5EntityEquipment(NPC npc) {
        super("Packet5EntityEquipment");
        super.setPublicValue("a", npc.getId());
    }

    public void setItemInHand(ItemStack itemStack){
        super.setPublicValue("b", 0);
        super.setPrivateValue("c", itemStack == null ? null : convertToNMS(itemStack));
    }

    public void setBoots(ItemStack itemStack){
        super.setPublicValue("b", 1);
        super.setPrivateValue("c", itemStack == null ? null : convertToNMS(itemStack));
    }

    public void setPants(ItemStack itemStack){
        super.setPublicValue("b", 2);
        super.setPrivateValue("c", itemStack == null ? null : convertToNMS(itemStack));
    }

    public void setBodyArmor(ItemStack itemStack){
        super.setPublicValue("b", 3);
        super.setPrivateValue("c", itemStack == null ? null : convertToNMS(itemStack));
    }

    public void setHelmet(ItemStack itemStack){
        super.setPublicValue("b", 4);
        super.setPrivateValue("c", itemStack == null ? null : convertToNMS(itemStack));
    }

    private Object convertToNMS(ItemStack itemStack){
        try{
            Method handle = ReflectionUtil.getMethod("getHandle", itemStack.getClass());
            Object nms_stack = handle.invoke(itemStack);
            return nms_stack;
        }catch(Exception e){
            NPCLib.instance.log(ChatColor.RED + "Failed to convert Bukkit itemstack to NMS itemstack!");
            return null;
        }
    }
}
