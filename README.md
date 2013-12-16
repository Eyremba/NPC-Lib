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

TODO list:
- create some more effect; sneaking, on fire, potion effect etc...
- add a walkTo(Location loc); method which will let the npc walk to a specific location.
- add a lookAt(Location loc); method which will let the npc look at a specific point.
- remove some bugs (the sleep stuff doesn't work properly)
- At the moment it's not possible to make an NPC do multiple actions at the same time. (blocking, crouching, etc)
























