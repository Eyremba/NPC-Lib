package com.captainbern.npclib.utils;

public class MathUtil {

    public static byte getCompressedAngle(float value) {
        return (byte) (value * 256.0F / 360.0F);
    }

    public static int asFixedPoint(double value) {
        return (int) (value * 32.0D);
    }

    public static float toDegree(double angle) {
        return (float) Math.toDegrees(angle);
    }
}
