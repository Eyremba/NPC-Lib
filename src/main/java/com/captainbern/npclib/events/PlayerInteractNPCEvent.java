package com.captainbern.npclib.events;

import com.captainbern.npclib.npc.NPC;
import com.captainbern.npclib.utils.Action;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerInteractNPCEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final NPC npc;
    private final Action action;
    private final Player player;

    public PlayerInteractNPCEvent(NPC npc, Action action, Player player) {
        this.npc = npc;
        this.action = action;
        this.player = player;
    }

    public Action getAction() {
        return action;
    }

    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
