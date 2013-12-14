package com.captainbern.npclib.wrappers;

import com.captainbern.npclib.NPCManager;

public class BasicWrapper {

    private Object handle;

    protected void setHandle(Object handle) {
        if(handle == null) {
            NPCManager.LOGGER_REFLECTION.warning("Cannot set Wrapper-handle to NULL!");
            return;
        }
        this.handle = handle;
    }

    public Object getHandle() {
        return handle;
    }
}
