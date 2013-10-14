package common.captainbern.npclib.wrapper.packet;

import org.bukkit.Location;

public class Packet33RelEntityMoveLook extends Packet30Entity {

    public Packet33RelEntityMoveLook(){
        super.setBooleanG(true);
    }

    public Packet33RelEntityMoveLook(int id){
        super.setEntityID(id);
        super.setBooleanG(true);
    }

    public Packet33RelEntityMoveLook(int id, Location to){
        super.setEntityID(id);
        super.setBooleanG(true);
        this.setLocationTo(to);
    }

    public void setLocationTo(Location locationTo){
        super.setX(locationTo.getX());
        super.setY(locationTo.getY());
        super.setZ(locationTo.getZ());
        super.setPitch(locationTo.getPitch());
        super.setYaw(locationTo.getYaw());
    }

}
