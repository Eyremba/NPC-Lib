package com.captainbern.npclib.injector;

import com.captainbern.npclib.NPCManager;
import com.captainbern.npclib.events.PlayerInteractNPCEvent;
import com.captainbern.npclib.utils.Action;
import com.captainbern.npclib.utils.PacketUtil;
import com.captainbern.npclib.utils.protocol.Packet;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.channel.ChannelInboundHandlerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import sun.management.resources.agent;

public class NPCHandler extends ChannelInboundHandlerAdapter {

    private Player player;

    public NPCHandler(Player player) {
        this.player = player;
    }

    @Override
    public void channelRead(ChannelHandlerContext cxt, Object message) throws Exception {
        if(message.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
            Packet packet = new Packet(message);
            int id = packet.read("a");
            Action action = PacketUtil.readAction(packet.read("b"));
            if(NPCManager.getInstance().isNPC(id)) {
                PlayerInteractNPCEvent event = new PlayerInteractNPCEvent(NPCManager.getInstance().getNPC(id), action, player);
                Bukkit.getPluginManager().callEvent(event);
            }
        }
        super.channelRead(cxt, message);
    }
}
