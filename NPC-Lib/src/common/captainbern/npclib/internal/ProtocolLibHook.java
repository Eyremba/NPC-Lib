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
import common.captainbern.npclib.NPCManager;
import common.captainbern.npclib.entity.NPC;

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

						/*
						 * This is something weird; the protocol page states that this is a boolean 
						 * and when true = left click, when false = right click but the source code 
						 * states that the action field is an integer; so I'm guessing 0 = right click and 
						 * 1 = left click (if I'm wrong; feel free to send me a pm on the forums http://forums.bukkit.org/members/captainbern.90729934/ )
						 */
						int action = packet.getSpecificModifier(int.class).read(2);

						NPCManager npcm = NPCLib.instance.getNPCManager();
						if(npcm.getNpcById(target) != null){
							if(action == 0){
								//call new "NPCInteractEvent"
							}else if(action == 1){
								NPC npc = npcm.getNpcById(target);
								//npc.hurt();
							}
						}
					} catch (FieldAccessException e) {
						NPCLib.instance.log(ChatColor.RED + "Could not acces a field in packet 0x07! (used to listen for interaction with npc's!");
					}
				}
			}
		});
	}
}
