package com.example.px_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.px_service.domain.OperateLog;

//@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLog> {
//    @Insert("insert into operate_log(operate_emp_id,operate_time,class_name,method_name,method_params,return_value,cost_time) " +
//            "values(#{operateEmpId},#{operateTime},#{className},#{methodName},#{methodParams},#{returnValue},#{costTime})")
//    public void insert(OperateLog operateLog);
}
