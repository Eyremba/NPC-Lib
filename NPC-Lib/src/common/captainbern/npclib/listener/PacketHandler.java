package common.captainbern.npclib.listener;

import common.captainbern.npclib.NPCLib;
import common.captainbern.npclib.entity.NPC;
import common.captainbern.npclib.events.PlayerDamageNpcEvent;
import common.captainbern.npclib.events.PlayerInteractNpcEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class PacketHandler {

    public void handlePacketAdd(Object packet, Player owner){
        if(packet.getClass().getName().endsWith("Packet7UseEntity")){
            try {
                Class<?> clazz = packet.getClass();
                Field click_field = clazz.getField("action");
                click_field.setAccessible(true);
                int clickaction = (Integer) click_field.get(packet); // 1 = left click, 0 = right click
                int entity_clicked = (Integer) clazz.getField("target").get(packet);

                if(NPCLib.instance.getNPCManager().getNpcById(entity_clicked) != null){
                    NPC npc = NPCLib.instance.getNPCManager().getNpcById(entity_clicked);
                    switch(clickaction){
                        case 0 :
                            PlayerInteractNpcEvent interact_event = new PlayerInteractNpcEvent(owner, npc);
                            Bukkit.getPluginManager().callEvent(interact_event);
                            break;
                        case 1 :
                            PlayerDamageNpcEvent hurt_event = new PlayerDamageNpcEvent(owner, npc);
                            Bukkit.getPluginManager().callEvent(hurt_event);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
