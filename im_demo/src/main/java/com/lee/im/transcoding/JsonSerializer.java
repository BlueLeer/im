package com.lee.im.transcoding;

import com.alibaba.fastjson.JSON;
import com.lee.im.constant.SerializerAlgorithm;

/**
 * @author WangLe
 * @date 2019/6/17 14:54
 * @description
 */
public class JsonSerializer implements Serializer {
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    public byte[] serialize(Object object) {
        // 使用Alibaba的的fastjson作为JSON的转换器的实现
        return JSON.toJSONBytes(object);
    }

    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
