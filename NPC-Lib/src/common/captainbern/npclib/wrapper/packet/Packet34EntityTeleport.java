package common.captainbern.npclib.wrapper.packet;

import common.captainbern.reflection.packet.LazyPacket;
import org.bukkit.Location;

public class Packet34EntityTeleport extends LazyPacket {

    public Packet34EntityTeleport(){
        super("Packet34EntityTeleport");
    }

    public void setEntityID(int id){
        super.setPublicValue("a", id);
    }

    public void setDestination(Location location){
        super.setPublicValue("b", (int) location.getX() * 32);
        super.setPublicValue("c", (int) location.getY() * 32);
        super.setPublicValue("d", (int) location.getZ() * 32);
        super.setPublicValue("e", (byte) getCompressedAngle(location.getPitch()));
        super.setPublicValue("f", (byte) getCompressedAngle(location.getYaw()));
    }

    public Packet34EntityTeleport(int id, Location location) {
        super("Packet34EntityTeleport");
        setEntityID(id);
        setDestination(location);
    }

    private byte getCompressedAngle(float value) {
        return (byte) (value * 256.0F / 360.0F);
    }
}
