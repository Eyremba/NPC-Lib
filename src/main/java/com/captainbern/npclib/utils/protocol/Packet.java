package com.captainbern.npclib.utils.protocol;

import com.captainbern.npclib.NPCManager;
import com.captainbern.npclib.reflection.SafeField;
import com.captainbern.npclib.utils.PacketUtil;
import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.List;

public class Packet {

    private Object handle;
    private List<SafeField> fields;

    public Packet(Protocol protocol, Sender sender, int id) {
        this.handle = PacketUtil.getPacket(protocol, sender, id);

        fields = Lists.newArrayList();

        for(Field field : handle.getClass().getDeclaredFields()) {
            fields.add(new SafeField(field));
        }
    }

    public Packet(Object handle) {
        if(handle == null) {
            NPCManager.LOGGER_REFLECTION.warning("Cannot create a Packet with a NULL handle!");
            return;
        }

        this.handle = handle;

        fields = Lists.newArrayList();

        for(Field field : handle.getClass().getDeclaredFields()) {
            fields.add(new SafeField(field));
        }
    }

    public void write(int index, Object value) {
        fields.get(index).set(handle, value);
    }

    public void write(String name, Object value) {
        new SafeField(handle.getClass(), name).set(handle, value);
    }

    public Object read(int index) {
        return fields.get(index).get(handle);
    }

    public <T> T read(String name) {
        return new SafeField<T>(handle.getClass(), name).get(handle);
    }
}
