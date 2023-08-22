package com.chen.yygh.user.api;


import com.chen.commonutil.result.Result;
import com.chen.commonutil.utils.AuthContextHolder;
import com.chen.model.model.user.UserInfo;
import com.chen.model.vo.user.LoginVo;
import com.chen.model.vo.user.UserAuthVo;
import com.chen.yygh.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {

    @Autowired
    private UserInfoService userInfoService;

    //用户手机号登录接口
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        Map<String,Object> info = userInfoService.loginUser(loginVo);
        return Result.ok(info);
    }

    /*//用户认证接口
    @PostMapping("auth/userAuth")
    public Result userAuth(@RequestBody UserAuthVo userAuthVo, HttpServletRequest request) {
        //其实就是往user_info补充添加一些认证数据进去，根据id查出记录会修改记录
        //传递两个参数，第一个参数用户id，第二个参数认证数据vo对象
        userInfoService.userAuth(AuthContextHolder.getUserId(request),userAuthVo);
        return Result.ok();
    }

    //获取用户id信息接口
    @GetMapping("auth/getUserInfo")
    public Result getUserInfo(HttpServletRequest request) {
        Long userId = AuthContextHolder.getUserId(request);
        UserInfo userInfo = userInfoService.getById(userId);
        return Result.ok(userInfo);
    }*/
}
