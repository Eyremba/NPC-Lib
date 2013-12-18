package com.captainbern.npclib.wrappers;

import com.captainbern.npclib.NPCManager;
import com.captainbern.npclib.reflection.ReflectionUtil;
import com.captainbern.npclib.utils.EntityUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.lang.reflect.Method;

public class DataWatcher extends BasicWrapper {

    private final Method RETURN_ALL_WATCHED = ReflectionUtil.getMethod(ReflectionUtil.getNMSClass("DataWatcher"), "c");
    private final Method UNWATCH_AND_RETURN_ALL_WATCHED = ReflectionUtil.getMethod(ReflectionUtil.getNMSClass("DataWatcher"), "b");
    //this should be improved because now it spawns a chicken and I hate chickens.
    public DataWatcher() {
        Entity fake = Bukkit.getWorlds().get(0).spawnEntity(new Location(Bukkit.getWorlds().get(0), 0, -5, 0), EntityType.CHICKEN);

        try {
            setHandle(ReflectionUtil.getNMSClass("DataWatcher").getDeclaredConstructor(new Class[]{ReflectionUtil.getNMSClass("Entity")}).newInstance(EntityUtil.getHandle(fake)));
        } catch (Exception e) {
            NPCManager.LOGGER_REFLECTION.warning("Failed to create new DataWatcher!");
            e.printStackTrace();
        }

    }

    public Object getAllWatched() {
        return ReflectionUtil.invokeMethod(RETURN_ALL_WATCHED, getHandle());
    }

    public Object unwatchAndReturnAllWatched() {
        return ReflectionUtil.invokeMethod(UNWATCH_AND_RETURN_ALL_WATCHED, getHandle());
    }

    public void write(int i, Object object){
        ReflectionUtil.invokeMethod(ReflectionUtil.getMethod(getHandle().getClass(), "a", int.class, Object.class), getHandle(), i, object);
    }
}
