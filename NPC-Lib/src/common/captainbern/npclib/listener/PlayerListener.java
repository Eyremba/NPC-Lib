package common.captainbern.npclib.listener;

import common.captainbern.npclib.NPCLib;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
        NPCLib.instance.getPlayerHook().hookPlayer(event.getPlayer(), true);
	}

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        NPCLib.instance.getPlayerHook().hookPlayer(event.getPlayer(), false);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        //check hook + send npc data.
    }

}
