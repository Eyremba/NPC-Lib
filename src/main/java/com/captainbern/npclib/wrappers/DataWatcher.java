package com.captainbern.npclib.wrappers;

import com.captainbern.npclib.NPCManager;
import com.captainbern.npclib.reflection.ReflectionUtil;
import com.captainbern.npclib.utils.EntityUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class DataWatcher extends BasicWrapper {

    public DataWatcher() {
        Entity fake = Bukkit.getWorlds().get(0).spawnEntity(new Location(Bukkit.getWorlds().get(0), 0, -5, 0), EntityType.CHICKEN);

        try {
            setHandle(ReflectionUtil.getNMSClass("DataWatcher").getDeclaredConstructor(new Class[]{ReflectionUtil.getNMSClass("Entity")}).newInstance(EntityUtil.getHandle(fake)));
        } catch (Exception e) {
            NPCManager.LOGGER_REFLECTION.warning("Failed to create new DataWatcher!");
            e.printStackTrace();
        }

    }

    public void write(int i, Object object){
        ReflectionUtil.invokeMethod(ReflectionUtil.getMethod(getHandle().getClass(), "a", int.class, Object.class), getHandle(), i, object);
    }
}
