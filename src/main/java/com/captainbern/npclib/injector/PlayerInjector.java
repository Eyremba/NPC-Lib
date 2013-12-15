package com.captainbern.npclib.injector;

import com.captainbern.npclib.utils.PlayerUtil;
import org.bukkit.entity.Player;

public class PlayerInjector {

    public static void injectPlayer(Player player) {
        PlayerUtil.getChannel(player).pipeline().addBefore("packet_handler", "npc_handler", new NPCHandler(player));
    }

    public static void uninjectPlayer(Player player) {
        PlayerUtil.getChannel(player).pipeline().remove("npc_handler");
    }
}
