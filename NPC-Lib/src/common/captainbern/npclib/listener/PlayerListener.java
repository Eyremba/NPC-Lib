package common.captainbern.npclib.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		 //hook player connection
	}

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        //clear player connection stuff
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        //check hook + send npc data.
    }

}
