package com.captainbern.npclib.injector;

import com.captainbern.npclib.utils.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.concurrent.Callable;

public class PlayerInjector {

    public static void injectPlayer(Player player) {
        PlayerUtil.getChannel(player).pipeline().addBefore("packet_handler", "npc_handler", new NPCHandler(player));
    }

    public static void uninjectPlayer(final Player player) {
        PlayerUtil.getChannel(player).eventLoop().submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                PlayerUtil.getChannel(player).pipeline().remove("npc_handler");
                return null;
            }
        });
    }
}
