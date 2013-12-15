package com.captainbern.npclib;

import org.bukkit.plugin.java.JavaPlugin;

public class Test extends JavaPlugin {

    NPCManager manager;

    @Override
    public void onEnable() {
        manager = new NPCManager(this);
    }

    @Override
    public void onDisable() {
        manager.shutdown();
    }
}
