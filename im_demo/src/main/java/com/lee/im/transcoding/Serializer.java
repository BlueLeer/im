package com.lee.im.transcoding;

/**
 * @author WangLe
 * @date 2019/6/17 14:49
 * @description 序列化器
 */
public interface Serializer {
    // 序列化算法的类型以及默认的序列化算法
    /**
     * json 序列化
     */
    byte JSON_SERIALIZER = 1;

    /**
     * 默认的序列化器
     */
    Serializer DEFAULT = new JsonSerializer();

    /**
     * 序列化算法
     *
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * 对象转化为二进制
     *
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 二进制转化成java对象
     *
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
