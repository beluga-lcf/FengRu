package com.prototyping.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("project2spec")
public class Project2Spec {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer pid;
    private Integer sid;
}
