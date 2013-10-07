package common.captainbern.npclib.wrapper.packet;

import common.captainbern.npclib.entity.NPC;
import common.captainbern.reflection.packet.LazyPacket;

public class Packet18ArmAnimation extends LazyPacket{

    public Packet18ArmAnimation(NPC npc, int action) {
        super("Packet18ArmaAnimation");
        super.setPublicValue("a", npc.getId());
        super.setPublicValue("b", action);
    }
}
