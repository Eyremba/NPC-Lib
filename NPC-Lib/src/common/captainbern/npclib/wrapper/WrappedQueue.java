package common.captainbern.npclib.wrapper;

import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentLinkedQueue;

public class WrappedQueue<E> extends ConcurrentLinkedQueue<E> {

    private Player owner;
    private ConcurrentLinkedQueue clq;

    public WrappedQueue(Player owner, ConcurrentLinkedQueue queue){
          this.owner = owner;
          this.clq = queue;
    }

    @Override
    public boolean add(E e){
        //incoming packet!
        //check packet for what we need
        //add it to the "super" queue
        // debug message down here to see if it works...
        System.out.print("Detected incoming packet! owner = {" + owner.getName() + "}" + " packet = {" + e.getClass().getName() + "}");
        return super.add(e);
    }

}
