package com.github.entrypointkr.messagereplacer;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class Reflections {
    public static final String NMS_PACKAGE_NAME = "net.minecraft.server." + getPackageVersion();
    public static final String OBC_PACKAGE_NAME = "org.bukkit.craftbukkit." + getPackageVersion();
    public static final Class<?> I_CHAT_BASE_COMPONENT = getNMSClass("IChatBaseComponent");
    public static final Class<?> PACKET_OUT_CHAT = getNMSClass("PacketPlayOutChat");
    public static final Class<?> CRAFT_CHAT_MESSAGE = getOBCClass("util.CraftChatMessage");

    public static String getPackageVersion() {
        String obcPackageName = Bukkit.getServer().getClass().getName();
        String sliced = obcPackageName.substring(obcPackageName.indexOf(".v") + 1);
        return sliced.substring(0, sliced.indexOf('.'));
    }

    public static Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static Class<?> getNMSClass(String name) {
        return getClass(NMS_PACKAGE_NAME + '.' + name);
    }

    public static Class<?> getOBCClass(String name) {
        return getClass(OBC_PACKAGE_NAME + '.' + name);
    }

    public static Field findDeclaredField(Class aClass, Class<?> fieldType, int index) throws NoSuchFieldException {
        Field[] fields = aClass.getDeclaredFields();
        int find = 0;
        for (Field field : fields) {
            if (fieldType.isAssignableFrom(field.getType())) {
                if (find++ == index) {
                    field.setAccessible(true);
                    return field;
                }
            }
        }
        if (aClass.getSuperclass() != Object.class) {
            return findDeclaredField(aClass.getSuperclass(), fieldType, index);
        }
        throw new NoSuchFieldException();
    }

    public static Method findDeclaredMethod(Class aClass, String methodName, int paramLength) throws NoSuchMethodException {
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName) && method.getParameterCount() == paramLength) {
                return method;
            }
        }
        throw new NoSuchMethodException();
    }

    public static String chatComponentToFlatString(Object component) {
        try {
            Method method = findDeclaredMethod(component.getClass(), "c", 0);
            return (String) method.invoke(component);
        } catch (Exception ex) {
            throw new IllegalStateException();
        }
    }

    public static Object[] createChatComponent(String message) {
        try {
            Method fromStringMethod = findDeclaredMethod(CRAFT_CHAT_MESSAGE, "fromString", 1);
            return (Object[]) fromStringMethod.invoke(null, message);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    private Reflections() {
    }
}
