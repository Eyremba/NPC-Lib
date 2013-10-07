package common.captainbern.npclib.wrapper.packet;

import common.captainbern.npclib.entity.NPC;
import common.captainbern.reflection.packet.LazyPacket;

public class Packet29DestroyEntity extends LazyPacket {

    public Packet29DestroyEntity(NPC npc) {
        super("Packet29DestroyEntity");
        super.setPublicValue("a", new int[]{npc.getId()});
    }
}
