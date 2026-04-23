package com.example.px_service.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OperateLog {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer operateEmpId;//操作人ID
    private LocalDateTime operateTime;//操作时间
    private String className;//操作类名
    private String methodName;//操作方法名
    private String methodParams;//方法参数
    private String returnValue;//返回值
    private Long costTime;//方法执行耗时，单位ms

}
