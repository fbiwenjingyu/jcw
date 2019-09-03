package com.ovit.jcw.utils;

import java.lang.reflect.*;

public class EnumUtil
{
    public static <T extends Enum<T>> T indexOf(final Class<T> clazz, final int ordinal) {
        return clazz.getEnumConstants()[ordinal];
    }
    
    public static <T extends Enum<T>> T nameOf(final Class<T> clazz, final String name) {
        return Enum.valueOf(clazz, name);
    }
    
    public static <T extends Enum<T>> T getByStringTypeCode(final Class<T> clazz, final String getTypeCodeMethodName, final String typeCode) {
        T result = null;
        try {
            final T[] arr = clazz.getEnumConstants();
            final Method targetMethod = clazz.getDeclaredMethod(getTypeCodeMethodName, (Class<?>[])new Class[0]);
            String typeCodeVal = null;
            for (final T entity : arr) {
                typeCodeVal = targetMethod.invoke(entity, new Object[0]).toString();
                if (typeCodeVal.contentEquals(typeCode)) {
                    result = entity;
                    break;
                }
            }
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        }
        catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
        catch (NoSuchMethodException e4) {
            e4.printStackTrace();
        }
        catch (SecurityException e5) {
            e5.printStackTrace();
        }
        return result;
    }
    
    public static <T extends Enum<T>> T getByIntegerTypeCode(final Class<T> clazz, final String getTypeCodeMethodName, final Integer typeCode) {
        T result = null;
        try {
            final T[] arr = clazz.getEnumConstants();
            final Method targetMethod = clazz.getDeclaredMethod(getTypeCodeMethodName, (Class<?>[])new Class[0]);
            Integer typeCodeVal = null;
            for (final T entity : arr) {
                typeCodeVal = Integer.valueOf(targetMethod.invoke(entity, new Object[0]).toString());
                if (typeCodeVal.equals(typeCode)) {
                    result = entity;
                    break;
                }
            }
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        }
        catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
        catch (NoSuchMethodException e4) {
            e4.printStackTrace();
        }
        catch (SecurityException e5) {
            e5.printStackTrace();
        }
        return result;
    }
    
    public static <T extends Enum<T>> T getByStringTypeName(final Class<T> clazz, final String getTypeNameMethodName, final String typeName) {
        T result = null;
        try {
            final T[] arr = clazz.getEnumConstants();
            final Method targetMethod = clazz.getDeclaredMethod(getTypeNameMethodName, (Class<?>[])new Class[0]);
            String typeNameVal = null;
            for (final T entity : arr) {
                typeNameVal = targetMethod.invoke(entity, new Object[0]).toString();
                if (typeNameVal.contentEquals(typeName)) {
                    result = entity;
                    break;
                }
            }
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        }
        catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
        catch (NoSuchMethodException e4) {
            e4.printStackTrace();
        }
        catch (SecurityException e5) {
            e5.printStackTrace();
        }
        return result;
    }
}
