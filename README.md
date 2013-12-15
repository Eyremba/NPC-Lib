NPC-Lib
=======

This is a proof of concept npc library. It only uses packets and some
"hacks" to spawn npc's. You can copy this library in your plugin (if your plugin is licensed under
GPL v3)

When you decide to use this in your plugin than your main class should contain this:
```java
NPCManager manager;

@Override
public void onEnable() {
manager = new NPCManager(this);
}

@Override
public void onDisable() {
manager.shutdown();
}
```

It's important this code is included in your plugin.
If not than the lib may not work as supposed to.
























