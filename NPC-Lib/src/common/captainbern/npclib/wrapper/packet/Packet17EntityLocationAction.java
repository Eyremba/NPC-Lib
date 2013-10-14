package common.captainbern.npclib.wrapper.packet;

import common.captainbern.reflection.packet.LazyPacket;
import org.bukkit.Location;

public class Packet17EntityLocationAction extends LazyPacket{

    public static int ACTION_SLEEP = 0;

    public Packet17EntityLocationAction(){
        super("Packet17EntityLocationAction");
    }

    public void setEntityID(int id){
        super.setPublicValue("a", id);
    }

    public void setAction(int action){
        super.setPublicValue("b", action);
    }

    public void setLocation(Location location){
        super.setPublicValue("b", (int) location.getX());
        super.setPublicValue("c", (int) location.getY());
        super.setPublicValue("d", (int) location.getZ());
    }

    public Packet17EntityLocationAction(int id, int action, Location loc) {
        super("Packet17EntityLocationAction");
        setEntityID(id);
        setAction(action);
        setLocation(loc);
    }
}
