package com.chen.yygh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.model.model.user.UserInfo;
import com.chen.model.vo.user.LoginVo;

import java.util.Map;

public interface UserInfoService extends IService<UserInfo> {
    //用户手机号登录接口
    Map<String, Object> loginUser(LoginVo loginVo);

    //根据openid判断
    UserInfo selectWxInfoOpenId(String openid);
}
