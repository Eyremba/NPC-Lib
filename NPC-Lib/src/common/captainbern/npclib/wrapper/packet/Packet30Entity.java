package common.captainbern.npclib.wrapper.packet;

import common.captainbern.reflection.packet.LazyPacket;

public class Packet30Entity extends LazyPacket {

    public Packet30Entity() {
        super("Packet30Entity");
    }

    public void setEntityID(int id){
        super.setPublicValue("a", id);
    }

    public void setX(double x){
        super.setPublicValue("b", getCompressCoord(x));
    }

    public void setY(double y){
        super.setPublicValue("c", getCompressCoord(y));
    }

    public void setZ(double z){
        super.setPublicValue("d", getCompressCoord(z));
    }

    public void setPitch(float pitch){
        super.setPublicValue("f", getCompressedAngle(pitch));
    }

    public void setYaw(float yaw){
        super.setPublicValue("g", getCompressedAngle(yaw));
    }

    public void setBooleanG(boolean bool){
        super.setPublicValue("g", bool);
    }

    private int getCompressCoord(double value){
        return (int) value * 32;
    }

    private byte getCompressedAngle(float value) {
        return (byte) (value * 256.0F / 360.0F);
    }

}
