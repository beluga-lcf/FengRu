package com.prototyping.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.prototyping.backend.entity.Project;
import com.prototyping.backend.entity.Project2Spec;
import com.prototyping.backend.entity.Spec;
import com.prototyping.backend.mapper.Project2SpecMapper;
import com.prototyping.backend.mapper.ProjectMapper;
import com.prototyping.backend.mapper.SpecMapper;
import com.prototyping.backend.service.IProjectService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements IProjectService {

    private ProjectMapper projectMapper;
    private SpecMapper specMapper;
    private Project2SpecMapper project2specMapper;

    @Autowired
    public void setProjectMapper(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }
    @Autowired
    public void setSpecMapper(SpecMapper specMapper) {
        this.specMapper = specMapper;
    }

    @Override
    public Integer createWithOneSpec(Object data) {
//        Project project = new Project();
//        project.setName("test");
//
//        projectMapper.insert(project);
        //        System.out.println(data);
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectNode node = mapper.createObjectNode();
//        node.put("id", 1);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.convertValue(data, ObjectNode.class);
        System.out.println(node);

        Project project = new Project();
        project.setName(node.get("project").get("name").asText());
        projectMapper.insert(project);

        int pid = project.getId();

        Spec spec = new Spec();
        spec.setPid(pid);
        spec.setName(node.get("spec").get("name").asText());
        spec.setWidth(node.get("spec").get("width").asInt());
        spec.setHeight(node.get("spec").get("height").asInt());
        specMapper.insert(spec);

        return 0;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectMapper.selectList(null);
    }

    // ctrl+i 可以找到未实现的方法，并自动补全
    @Override
    public List<Project> getDeletedProjects() {
        return  projectMapper.loadDeletedProjects();
    }


    @Override
    public void deleteProjectById(Integer id) {
        projectMapper.deleteById(id);
    }

    @Override
    public void renameProjectById(Integer id, String name) {
        Project project = projectMapper.selectById(id);
        project.setName(name);
        projectMapper.updateById(project);
    }

    @Override
    public Integer createProject(String name) {
        Project project = new Project();
        project.setName(name);
        projectMapper.insert(project);
        return project.getId();
    }

    @Override
    public void restoreProject(Integer id) {
        projectMapper.restoreProject(id);
    }

    @Override
    public Integer queryNumOfSpecs(Integer id) {
        QueryWrapper<Project2Spec> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", id);
        return Math.toIntExact(project2specMapper.selectCount(queryWrapper));
    }

}
