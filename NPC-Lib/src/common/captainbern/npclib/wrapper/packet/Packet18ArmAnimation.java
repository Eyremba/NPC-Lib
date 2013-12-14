package common.captainbern.npclib.wrapper.packet;

import common.captainbern.reflection.packet.LazyPacket;

public class Packet18ArmAnimation extends LazyPacket{

    public static int ACTION_SWING_ARM = 1;
    public static int ACTION_HURT = 2;
    public static int ACTION_WAKE_UP = 3;

    public Packet18ArmAnimation(){
        super("Packet18ArmAnimation");
    }

    public void setEntityID(int id){
        super.setPublicValue("a",id);
    }

    public void setAction(int action_id){
        super.setPublicValue("b", action_id);
    }

    public Packet18ArmAnimation(int id, int action) {
        super("Packet18ArmaAnimation");
        super.setPublicValue("a", id);
        super.setPublicValue("b", action);
    }
}
