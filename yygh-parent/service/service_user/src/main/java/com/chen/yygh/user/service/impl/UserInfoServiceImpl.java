package com.chen.yygh.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.commonutil.exception.HospitalException;
import com.chen.commonutil.helper.JwtHelper;
import com.chen.commonutil.result.ResultCodeEnum;
import com.chen.model.model.user.UserInfo;
import com.chen.model.vo.user.LoginVo;
import com.chen.yygh.user.mapper.UserInfoMapper;
import com.chen.yygh.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserInfoServiceImpl extends
        ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public Map<String, Object> loginUser(LoginVo loginVo) {
        //从loginVo获取输入的手机号，和验证码
        String phone = loginVo.getPhone();
        String code = loginVo.getCode();

        //判断手机号和验证码是否为空
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            throw new HospitalException(ResultCodeEnum.PARAM_ERROR);
        }

        //判断手机验证码和输入的验证码是否一致
        String redisCode = redisTemplate.opsForValue().get(phone);
        if(!code.equals(redisCode)) {
            throw new HospitalException(ResultCodeEnum.CODE_ERROR);
        }

        //如果是微信扫码登录，则Openid有值，则绑定手机号码，执行后userInfo就!=null了
        UserInfo userInfo = null;
        if(!StringUtils.isEmpty(loginVo.getOpenid())) {
            userInfo = this.selectWxInfoOpenId(loginVo.getOpenid());
            if(null != userInfo) {
                userInfo.setPhone(loginVo.getPhone());
                this.updateById(userInfo);
            } else {
                throw new HospitalException(ResultCodeEnum.DATA_ERROR);
            }
        }
        //如果userinfo为空，进行正常手机登录
        if(userInfo == null) {
            //判断是否第一次登录：根据手机号查询数据库，如果不存在相同手机号就是第一次登录
            QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("phone",phone);
            userInfo = baseMapper.selectOne(wrapper);
            //如果userInfo不为null，则不执行if里面
            if(userInfo == null) { //第一次使用这个手机号登录
                //添加信息到数据库
                userInfo = new UserInfo();
                userInfo.setName("");
                userInfo.setPhone(phone);
                userInfo.setStatus(1);
                baseMapper.insert(userInfo);
            }
        }

        //校验是否被禁用
        if(userInfo.getStatus() == 0) {
            throw new HospitalException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        //不是第一次登录，直接登录。假设用户是userInfo == null手机号登录的，并且已经注册过不用走73行的代码了，直接走下面的即可。
        //返回登录信息
        //返回登录用户名
        //返回token信息，token信息是用来返回给前台的，执行操作时判断用户是否登录状态，可以设置过期时间用session一样
        Map<String, Object> map = new HashMap<>();
        String name = userInfo.getName();
        //如果这个用户登录后没有去设置真实姓名，则name为空，那我们就设置该用户它在前端显示的名字为昵称
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        //如果这个用户登录后也没有设置昵称，则name还是空，那我们就设置该用户它在前端显示的名字为它的手机号
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getPhone();
        }
        map.put("name",name);

        //jwt生成token字符串
        String token = JwtHelper.createToken(userInfo.getId(), name);
        map.put("token",token);
        return map;
    }

    @Override
    public UserInfo selectWxInfoOpenId(String openid) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        UserInfo userInfo = baseMapper.selectOne(queryWrapper);
        return userInfo;
    }
}
