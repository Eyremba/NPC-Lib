package com.captainbern.npclib.utils;

import com.captainbern.npclib.reflection.ReflectionUtil;
import org.bukkit.entity.Entity;

public class EntityUtil {

    public static Object getHandle(Entity entity) {
        return ReflectionUtil.invokeMethod(ReflectionUtil.getMethod(entity.getClass(), "getHandle"), entity);
    }
}
