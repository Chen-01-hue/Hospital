package com.chen.yygh.msm.receiver;


import com.chen.model.vo.msm.MsmVo;
import com.chen.yygh.msm.service.MsmService;

import org.springframework.stereotype.Component;

@Component
public class MsmReceiver {

   /* @Autowired
    private MsmService smsService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_MSM_ITEM, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_MSM),
            key = {MqConst.ROUTING_MSM_ITEM}
    ))
    public void send(MsmVo msmVo, Message message, Channel channel) {
        smsService.send(msmVo);
    }*/
}
