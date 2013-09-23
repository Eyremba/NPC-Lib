package common.captainbern.reflection.packet;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import common.captainbern.reflection.ReflectionUtil;

public class LazyPacket {
	
	private Object crafted_packet = null;

	/**
	 * This is a little class that makes it possible for me 
	 * to easily craft/send packets. It has been created with the
	 * aim to make it as easy as possible.
	 */
	public LazyPacket(String name){
		try {
			crafted_packet = ReflectionUtil.getNMSClass(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets a public field value of a class/packet.
	 */
	public void setPublicValue(String field, Object value){
		try {
			Field f = crafted_packet.getClass().getField(field);
			f.setAccessible(true);
			f.set(crafted_packet, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets a private field value of a class/packet.
	 */
	public void setPrivateValue(String field, Object value){
		try {
			Field f = crafted_packet.getClass().getDeclaredField(field);
			f.setAccessible(true);
			f.set(crafted_packet, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the packet-object.
	 */
	public Object getPacketObject(){
		return this.crafted_packet;
	}

	/**
	 * Method used to send the packet to specified player.
	 */
	public void send(Player player){
		try {
			Object entityPlayer = ReflectionUtil.getMethod("getHandle", player.getClass(), 0).invoke(player);
			Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
			ReflectionUtil.getMethod("sendPacket", playerConnection.getClass(), 1).invoke(playerConnection, crafted_packet);
		} catch (Exception e) {
			Bukkit.getLogger().warning("[CBCommonLib] Failed to send packet to " + player.getName() + "!");
		}
	}
}
