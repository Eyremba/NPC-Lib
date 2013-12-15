package com.captainbern.npclib.utils;

import com.captainbern.npclib.reflection.ReflectionUtil;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class ItemUtil {

    private static final Method TO_NMS = ReflectionUtil.getMethod(ReflectionUtil.getCBClass("inventory.CraftItemStack"), "asNMSCopy", ItemStack.class);

    public static Object toNMS(ItemStack stack) {
        return ReflectionUtil.invokeMethod(TO_NMS, null, stack);
    }
}
