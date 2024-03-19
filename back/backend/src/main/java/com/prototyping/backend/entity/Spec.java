package com.prototyping.backend.entity;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName(value = "spec", autoResultMap = true)
public class Spec {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer pid;
    private String name;
    private Timestamp ctime;
    private Timestamp utime;
    private Integer width;
    private Integer height;
    @TableField(value = "jsonData")
    private String jsonData;
    @TableLogic
    private Integer isDeleted;
}
