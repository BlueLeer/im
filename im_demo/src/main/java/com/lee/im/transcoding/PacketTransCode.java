package com.lee.im.transcoding;

import com.lee.im.constant.Command;
import com.lee.im.constant.SerializerAlgorithm;
import com.lee.im.model.LoginRequestPacket;
import com.lee.im.model.LoginResponsePacket;
import com.lee.im.model.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author WangLe
 * @date 2019/6/17 15:16
 * @description 包的编解码过程
 */
public class PacketTransCode {
    private static final int MAGIC_NUMBER = 0X12345678;

    private PacketTransCode() {

    }

    public static PacketTransCode getInstance() {
        return PacketTransCodeHolder.INSTANCE;
    }

    private static class PacketTransCodeHolder {
        private static final PacketTransCode INSTANCE = new PacketTransCode();
    }

    /**
     * 编码
     *
     * @param packet
     * @return
     */
    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        // 调用 Netty 的 ByteBuf 分配器来创建，ioBuffer() 方法会返回适配 io 读写相关的内存，它会尽可能创建一个直接内存，直接内存可以理解为不受 jvm 堆管理的内存空间，写到 IO 缓冲区的效果更高。
//        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        // 协议包的构成:魔数(4字节)+版本号(1字节)+序列化算法(1字节)+指令(1字节)+数据长度(4字节)+数据(N字节)

        // 魔数
        byteBuf.writeInt(MAGIC_NUMBER);
        // 版本号
        byteBuf.writeByte(packet.getVersion());
        // 序列化算法
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        // 指令
        byteBuf.writeByte(packet.getCommand());
        // 数据长度
        byteBuf.writeInt(bytes.length);
        // 数据
        byteBuf.writeBytes(bytes);

        return byteBuf;

    }

    /**
     * 解码过程
     *
     * @param byteBuf
     * @return
     */
    public Packet decode(ByteBuf byteBuf) {
        // 跳过魔数
        byteBuf.skipBytes(4);
        // 跳过版本号
        byteBuf.skipBytes(1);
        // 序列化算法标识
        byte serializerAlgorithm = byteBuf.readByte();
        // 指令
        byte command = byteBuf.readByte();
        // 数据长度
        int dataLength = byteBuf.readInt();
        // 数据部分
        byte[] contents = new byte[dataLength];
        // 将数据读取到contents中
        byteBuf.readBytes(contents);

        // 根据不同的指令,不同的序列化算法,将字节数据解析成对应的java对象
        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerilizer(serializerAlgorithm);

        Packet packet = serializer.deserialize(requestType, contents);
        return packet;
    }

    private Serializer getSerilizer(byte serializerAlgorithm) {
        Serializer serializer = null;
        switch (serializerAlgorithm) {
            case SerializerAlgorithm.JSON: // 客户端登陆
                serializer = new JsonSerializer();
                break;
            default:
                serializer = null;
                break;
        }
        return serializer;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        Class<? extends Packet> clazz = null;
        switch (command) {
            case 1: // 客户端登陆
                clazz = LoginRequestPacket.class;
                break;
            case 2: // 登录响应
                clazz = LoginResponsePacket.class;
                break;
            case 3:
                break;
            default:
                clazz = null;
                break;
        }

        return clazz;
    }


}
