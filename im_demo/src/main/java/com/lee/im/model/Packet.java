package com.lee.im.model;

import lombok.Data;

/**
 * @author WangLe
 * @date 2019/6/17 14:09
 * @description
 * \@Data包含了下面几个注解的功能:@ToString @Getter @Setter @EqualsAndHashCode @NoArgsConstructor
 *
 * 所有指令数据包的 基类,它包含了协议的版本和获取指令的方式
 * <p>
 * 协议包的构成:魔数(4字节)+版本号(1字节)+序列化算法(1字节)+指令(1字节)+数据长度(4字节)+数据(N字节)
 */

@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     *
     * @return
     */
    public abstract Byte getCommand();

}
