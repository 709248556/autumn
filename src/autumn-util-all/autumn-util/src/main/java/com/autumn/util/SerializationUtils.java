package com.autumn.util;

import java.io.*;

/**
 * 序列化工具
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-09 2:05
 */
public class SerializationUtils {

    /**
     * Deep clone an {@code Object} using serialization.
     * <p>This is many times slower than writing clone methods by hand
     * on all objects in your object graph. However, for complex object
     * graphs, or for those that don't support deep cloning this can
     * be a simple alternative implementation. Of course all the objects
     * must be {@code Serializable}.</p>
     *
     * @param <T>    the type of the object involved
     * @param object the {@code Serializable} object to clone
     * @return the cloned object
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(final T object) {
        if (object == null) {
            return null;
        }
        final byte[] objectData = serialize(object);
        return (T) deserialize(objectData);
    }

    /**
     * 序列化对象
     *
     * @param object the object to serialize
     * @return an array of bytes representing the object in a portable fashion
     */
    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream(1024);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), ex);
        }
        return arrayOutputStream.toByteArray();
    }

    /**
     * 反序列化
     *
     * @param bytes a serialized object
     * @return the result of deserializing the bytes
     */
    public static Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return inputStream.readObject();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to deserialize object", ex);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Failed to deserialize object type", ex);
        }
    }
}
