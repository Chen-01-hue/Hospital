
import com.alibaba.fastjson.JSONObject;
import com.chen.yygh.msm.utils.HttpUtils;
import com.chen.yygh.msm.utils.RandomUtil;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import java.util.*;


public class MsmControllerTests {
    /**
     * 用阿里云 云市场商品里的短信接口
     */
    @Test
    public void aliYunSendSms(){
        //测试阿里云发送的短信验证码
        String host = "https://dfsmsv2.market.alicloudapi.com";
        String path = "/data/send_sms_v2";
        String method = "POST";
        String appcode = "b0d1da15b18544f0bdb646b36734587e";
        String code = RandomUtil.getFourBitRandom();
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
       /* querys.put("param", "**code**:"+code+",**minute**:5");
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("mobile", "15720959459");
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");*/
        Map<String, String> bodys = new HashMap<>();
        bodys.put("content", "code:" + code);
        bodys.put("template_id", "TPL_0000");
        bodys.put("phone_number", "15720959459");
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
            System.out.println("获取response："+response.toString());
            System.out.println("获取response的StatusCode："+response.getStatusLine().getStatusCode());
            //获取response的body
            System.out.println("获取response的body："+(response.getEntity()));
            String jsonData = EntityUtils.toString(response.getEntity());
            System.out.println("jsonData:" + jsonData);
            /*JSONObject retData = JSONObject.parseObject(jsonData);
            String balance = retData.getString("balance");
            System.out.println("可使用的短信余额数："+balance);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
