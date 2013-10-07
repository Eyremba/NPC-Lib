package common.captainbern.npclib.wrapper.packet;

import common.captainbern.npclib.entity.NPC;
import common.captainbern.reflection.packet.LazyPacket;
import org.bukkit.Location;

public class Packet17EntityLocationAction extends LazyPacket{

    public Packet17EntityLocationAction(NPC npc, int action) {
        super("Packet17EntityLocationAction");
        super.setPublicValue("a", npc.getId());
        super.setPublicValue("e", action);
        super.setPublicValue("b", (int) npc.getLocation().getX());
        super.setPublicValue("c", (int) npc.getLocation().getY());
        super.setPublicValue("d", (int) npc.getLocation().getZ());
    }

    public void setLocation(Location location){
        super.setPublicValue("b", (int) location.getX());
        super.setPublicValue("c", (int) location.getY());
        super.setPublicValue("d", (int) location.getZ());
    }
}
