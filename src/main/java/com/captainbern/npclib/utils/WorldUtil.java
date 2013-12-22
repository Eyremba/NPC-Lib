package com.captainbern.npclib.utils;

import com.captainbern.npclib.reflection.ReflectionUtil;
import org.bukkit.World;

import java.lang.reflect.Method;

public class WorldUtil {

    private static final Method GET_HANDLE = ReflectionUtil.getMethod(ReflectionUtil.getCBClass("CraftWorld"), "getHandle");

    public static Object getHandle(World world) {
        return ReflectionUtil.invokeMethod(GET_HANDLE, world);
    }
}
