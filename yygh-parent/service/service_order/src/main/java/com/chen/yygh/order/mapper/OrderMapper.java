package com.chen.yygh.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.model.model.order.OrderInfo;
import com.chen.model.vo.order.OrderCountQueryVo;
import com.chen.model.vo.order.OrderCountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<OrderInfo> {

    //查询预约统计数据的方法
    List<OrderCountVo> selectOrderCount(@Param("vo") OrderCountQueryVo orderCountQueryVo);
}
