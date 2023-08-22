package com.chen.yygh.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chen.yygh.msm.service.MsmService;
import com.chen.yygh.msm.utils.HttpUtils;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(String phone, String code) {
        //判断手机号是否为空
        if(StringUtils.isEmpty(phone)) {
            return false;
        }
        //整合阿里云短信服务
        //设置相关参数
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "b0d1da15b18544f0bdb646b36734587e";
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        querys.put("param", "**code**:" + code + ",**minute**:5");
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
        Map<String, String> bodys = new HashMap<>();
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println("可使用的短信余额数："+ JSONObject.parseObject(EntityUtils.toString(response.getEntity())).getString("balance"));
            return response.getStatusLine().getStatusCode() == 200 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*//mq发送短信封装
    @Override
    public boolean send(MsmVo msmVo) {
        if(!StringUtils.isEmpty(msmVo.getPhone())) {
            boolean isSend = this.send(msmVo.getPhone(), msmVo.getParam());
            return isSend;
        }
        return false;
    }

    private boolean send(String phone, Map<String,Object> param) {
        //判断手机号是否为空
        if(StringUtils.isEmpty(phone)) {
            return false;
        }
        //整合阿里云短信服务
        //设置相关参数
        DefaultProfile profile = DefaultProfile.
                getProfile(ConstantPropertiesUtils.REGION_Id,
                        ConstantPropertiesUtils.ACCESS_KEY_ID,
                        ConstantPropertiesUtils.SECRECT);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //手机号
        request.putQueryParameter("PhoneNumbers", phone);
        //签名名称
        request.putQueryParameter("SignName", "我的谷粒在线教育网站");
        //模板code
        request.putQueryParameter("TemplateCode", "SMS_180051135");

        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        //调用方法进行短信发送
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }*/
}
