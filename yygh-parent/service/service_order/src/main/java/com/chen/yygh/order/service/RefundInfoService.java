package com.chen.yygh.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.model.model.order.PaymentInfo;
import com.chen.model.model.order.RefundInfo;

public interface RefundInfoService extends IService<RefundInfo> {

    /**
     * 保存退款记录
     * @param paymentInfo
     */
    RefundInfo saveRefundInfo(PaymentInfo paymentInfo);

}
