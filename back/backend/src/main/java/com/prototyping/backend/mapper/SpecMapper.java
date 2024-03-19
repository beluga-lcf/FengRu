package com.prototyping.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.prototyping.backend.entity.Spec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SpecMapper extends BaseMapper<Spec> {
    @Select("select count(*) from spec where pid = #{pid}")
    Integer findSpecs(Integer pid);
}
