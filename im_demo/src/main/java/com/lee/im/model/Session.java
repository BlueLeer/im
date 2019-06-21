package com.lee.im.model;

import lombok.Data;

/**
 * @author WangLe
 * @date 2019/6/21 9:44
 * @description 用户登录以后的回话信息
 */
@Data
public class Session {
    private String userId;
    private String username;
}
