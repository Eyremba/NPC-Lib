/*
 * This class is the packet queue that will be send to the client.
 * It will contain several packet-wrapper-objects that contain data about the npc.
 * (eg: the spawn packet, move packets, equipment packet , etc...)
 */
package common.captainbern;

import java.util.LinkedList;

public class NPCPacketContainer<LazyPacket> {

    private LinkedList<LazyPacket> _inboundQueue = new LinkedList<LazyPacket>();

    public void loadPacket(LazyPacket lazyPacket){
        _inboundQueue.add(lazyPacket);
    }

    public void unloadPacket(LazyPacket lazyPacket){
        _inboundQueue.remove(lazyPacket);
    }

    public boolean isEmpty(){
        return _inboundQueue.isEmpty();
    }

    public void unload(int index){
        _inboundQueue.remove(index);
    }

    public int size(){
        return _inboundQueue.size();
    }

}
