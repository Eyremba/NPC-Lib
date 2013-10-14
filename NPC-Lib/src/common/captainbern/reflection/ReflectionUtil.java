package common.captainbern.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReflectionUtil {
	
	public static Object getNMSClass(String name, Object... args) throws Exception {
		Class<?> c = Class.forName(ReflectionUtil.getNMSPackageName() + "." + name);
		int params = 0;
		if (args != null) {
			params = args.length;
		}
		for (Constructor<?> co : c.getConstructors()) {
			if (co.getParameterTypes().length == params) {
				return co.newInstance(args);
			}
		}
		return null;
	}
	
	public static Class<?> getNMSClassExact(String name, Object... args){
		Class<?> c;
		try{
		c= Class.forName(ReflectionUtil.getNMSPackageName() + "." + name);
		}catch(Exception e){
			return null;
		}
		return c;
	}

    public static Object getOBCClass(String name, Object... args) throws Exception {
        Class<?> c = Class.forName(ReflectionUtil.getOBCPackageName() + "." + name);
        int params = 0;
        if (args != null) {
            params = args.length;
        }
        for (Constructor<?> co : c.getConstructors()) {
            if (co.getParameterTypes().length == params) {
                return co.newInstance(args);
            }
        }
        return null;
    }

    public static Class<?> getOBCClassExact(String name, Object... args){
        Class<?> c;
        try{
            c= Class.forName(ReflectionUtil.getOBCPackageName() + "." + name);
        }catch(Exception e){
            return null;
        }
        return c;
    }

	public static Method getMethod(String name, Class<?> c, int params) {
		for (Method m : c.getMethods()) {
			if (m.getName().equals(name) && m.getParameterTypes().length == params) {
				return m;
			}
		}
		return null;
	}
	
	public static Method getMethod(String name, Class<?> c) {
		for (Method m : c.getMethods()) {
			if (m.getName().equals(name)) {
				return m;
			}
		}
		return null;
	}

	public static Field getField(String name, Class<?> c) {
		for (Field f : c.getFields()) {
			if (f.getName().equals(name)) {
				return f;
			}
		}
		return null;
	}

	public static void setValue(Object instance, String fieldName, Object value) throws Exception {
		Field field = instance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(instance, value);
	}

	public static String getNMSPackageName() {
		return "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	}

    public static String getOBCPackageName(){
        return "org.bukkit.craftbukkit" + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }
	
	public static Object BukkitPlayerToEntityPlayer(Player player){
		Object entityPlayer = null;
		try{
		entityPlayer = ReflectionUtil.getMethod("getHandle", player.getClass(), 0).invoke(player);
		}catch(Exception e){
			return null;
		}
		return entityPlayer;
	}

}
