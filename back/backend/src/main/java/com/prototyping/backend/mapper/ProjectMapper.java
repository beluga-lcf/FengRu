package com.prototyping.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.prototyping.backend.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
    @Select("SELECT * FROM project WHERE is_deleted = 1")
    List<Project> loadDeletedProjects();

    @Select("update project set is_deleted = 0 where id = #{id}")
    void restoreProject(Integer id);
}
