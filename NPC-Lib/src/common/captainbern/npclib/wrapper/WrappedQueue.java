package common.captainbern.npclib.wrapper;

import common.captainbern.npclib.NPCLib;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentLinkedQueue;

public class WrappedQueue<E> extends ConcurrentLinkedQueue<E> {

    private Player owner;

    public WrappedQueue(Player owner){
          this.owner = owner;
    }

    @Override
    public boolean add(E e){
        //System.out.print("Detected incoming packet! owner = {" + owner.getName() + "}" + " packet = {" + e.getClass().getName() + "}");
        NPCLib.instance.getPacketHandler().handlePacketAdd(e, owner);
        return super.add(e);
    }

}
