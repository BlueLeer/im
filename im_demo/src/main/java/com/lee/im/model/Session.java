package com.lee.im.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WangLe
 * @date 2019/6/21 9:44
 * @description 用户登录以后的回话信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    private String userId;
    private String username;


}
