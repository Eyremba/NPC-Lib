package common.captainbern.npclib.events;

import common.captainbern.npclib.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerInteractNpcEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    private NPC npc;
    private Player player;

    public PlayerInteractNpcEvent(Player player, NPC npc){
        this.npc = npc;
        this.player = player;
    }

    public Player getPlayer(){
        return this.player;
    }

    public NPC getNpc(){
        return npc;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled(){
        return cancelled;
    }

    public void setCancelled(boolean arg0){
        this.cancelled = arg0;
    }

}
