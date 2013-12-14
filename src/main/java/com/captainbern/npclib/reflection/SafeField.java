package com.captainbern.npclib.reflection;

import com.captainbern.npclib.NPCManager;

import java.lang.reflect.Field;

public class SafeField<T> {

    private Field field;

    public SafeField(Field field) {
        if(!field.isAccessible()) {
            field.setAccessible(true);
        }
        this.field = field;
    }

    public SafeField(Class<?> clazz, String fieldName) {
        try {
            field = clazz.getDeclaredField(fieldName);

            if(!field.isAccessible()) {
                field.setAccessible(true);
            }
        } catch (Exception e) {
            NPCManager.LOGGER_REFLECTION.warning("Could not create SafeField!");
        }
    }

    public T get(Object instance) {
        try {
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            NPCManager.LOGGER_REFLECTION.warning("Could not access field: " + toString());
            return null;
        }
    }

    public void set(Object instance, Object value) {
        try {
            this.field.set(instance, value);
        } catch (IllegalAccessException e) {
            NPCManager.LOGGER_REFLECTION.warning("Could not access field: " + toString());
        }
    }
}
