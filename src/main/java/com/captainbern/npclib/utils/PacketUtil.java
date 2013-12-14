package com.captainbern.npclib.utils;

import com.captainbern.npclib.NPCManager;
import com.captainbern.npclib.reflection.ReflectionUtil;
import com.captainbern.npclib.reflection.SafeField;
import com.captainbern.npclib.utils.protocol.Protocol;
import com.captainbern.npclib.utils.protocol.Sender;

import java.lang.reflect.Method;
import java.util.Map;

public class PacketUtil {

    private static final SafeField<Map<Integer, Class<?>>> SERVER_PACKETS_MAP = new SafeField<Map<Integer, Class<?>>>(ReflectionUtil.getNMSClass("EnumProtocol"), "h");
    private static final SafeField<Map<Integer, Class<?>>> CLIENT_PACKETS_MAP = new SafeField<Map<Integer, Class<?>>>(ReflectionUtil.getNMSClass("EnumProtocol"), "i");

    private static final Method READ_ACTION = ReflectionUtil.getMethod(ReflectionUtil.getNMSClass("EnumEntityAction"), "a", ReflectionUtil.getNMSClass("EnumEntityUseAction"));

    public static Object getPacket(Protocol protocol, Sender sender, int id) {
        try{
            if(sender == Sender.CLIENT) {
                return CLIENT_PACKETS_MAP.get(protocol.toVanilla()).get(id).newInstance();
            }

            if(sender == Sender.SERVER) {
                return SERVER_PACKETS_MAP.get(protocol.toVanilla()).get(id).newInstance();
            }

            return null;
        }catch(Exception e) {
            NPCManager.LOGGER_REFLECTION.warning("Failed to retrieve the packet object for: " + protocol.toString() + ", " + sender.toString() + ", " + id );
            return null;
        }
    }

    public static Action readAction(Object enumAction) {
        return ReflectionUtil.invokeMethod(READ_ACTION, enumAction, enumAction);
    }
}
