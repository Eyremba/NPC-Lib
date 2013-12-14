package com.captainbern.npclib.utils;

import com.captainbern.npclib.npc.NPC;
import com.captainbern.npclib.utils.protocol.Packet;
import com.captainbern.npclib.utils.protocol.Protocol;
import com.captainbern.npclib.utils.protocol.Sender;

public class PacketFactory {

    public static Object craftSpawnPacket(NPC npc) {
        Packet packet = new Packet(Protocol.PLAY, Sender.SERVER, 20);
        packet.write("a", npc.getEntityID());
        packet.write("b", npc.getGameProfile());

        return packet.getHandle();
    }
}
