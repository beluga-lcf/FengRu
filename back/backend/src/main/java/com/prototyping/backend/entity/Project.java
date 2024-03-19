package com.prototyping.backend.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("project")
public class Project {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("uid")
    private Integer uid;
    private String name;
    private Timestamp ctime;
    private Timestamp utime;
    @TableLogic
    private int is_deleted;
}
