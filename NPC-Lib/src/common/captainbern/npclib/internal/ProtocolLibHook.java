package common.captainbern.npclib.internal;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;

import common.captainbern.npclib.NPCLib;

public class ProtocolLibHook {
	
	public void setUpInteractListener(){
		ProtocolManager pm = ProtocolLibrary.getProtocolManager();
		
		pm.addPacketListener(new PacketAdapter(NPCLib.instance, ConnectionSide.CLIENT_SIDE, 0x07){
			 @Override
             public void onPacketReceiving(PacketEvent event) {
                 Player player = event.getPlayer();
                 if (event.getPacketID() == 0x07) {
                     try {
                         PacketContainer packet = event.getPacket();
                         int target = packet.getSpecificModifier(int.class).read(1);
                         int action = packet.getSpecificModifier(int.class).read(2);
                         
                         if (packet.getEntityModifier(player.getWorld()).read(1) == null) {
                        	 //
                         }
                     } catch (FieldAccessException e) {
                         NPCLib.instance.log(ChatColor.RED + "Could not acces a field in packet 0x07! (used to listen for interaction with npc's!");
                     }
                 }
             }
		});
	}
}
