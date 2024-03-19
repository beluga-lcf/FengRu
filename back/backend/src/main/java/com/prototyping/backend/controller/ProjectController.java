package com.prototyping.backend.controller;

import com.prototyping.backend.entity.domain.Result;
import com.prototyping.backend.service.impl.ProjectServiceImpl;
import com.prototyping.backend.service.impl.SpecServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/project")
@Slf4j
public class ProjectController {
    private ProjectServiceImpl projectService;
    private SpecServiceImpl specService;

    @Autowired
    public void setCreateProject(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }

    @Autowired
    public void setSpecService(SpecServiceImpl specService) {
        this.specService = specService;
    }

    @GetMapping("/loadAllDeletedProjects")
    public Result<Object> loadAllDeletedProjects(){
        try {
            return Result.ok(projectService.getDeletedProjects());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.fail();
        }
    }
    @GetMapping("/loadAllProjects")
    public Result<Object> loadAllProject(){
        try {
            return Result.ok(projectService.getAllProjects());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.fail();
        }
    }
    @PostMapping("/deleteProjectById")
    public Result<Object> deleteProjectById(Integer id){
        try {
            projectService.deleteProjectById(id);
            return Result.ok(null, "OK");
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.fail();
        }
    }

    @PostMapping("/renameProject")
    public Result<Object> renameProjectById(Integer id,String name){
        try {
            projectService.renameProjectById(id,name);
            return Result.ok(null, "OK");
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.fail();
        }
    }

    @PostMapping("/createProject")
    public Result<Object> createProject(String name){
        try {
            projectService.createProject(name);
            return Result.ok(null, "OK");
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.fail();
        }
    }

    @PostMapping("/restoreProject")
    public Result<Object> restoreProject(Integer id){
        try {
            projectService.restoreProject(id);
            return Result.ok(null, "OK");
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.fail();
        }
    }

    @PostMapping("/queryNumOfSpecs")
    public Result<Object> queryNumOfSpecs(Integer id){
        try {
            return Result.ok(projectService.queryNumOfSpecs(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.fail();
        }
    }

    @PostMapping("/createProjectWithSpec")
    public Result<Object> createProjectWithSpec(String pname, String sname,Integer width, Integer height){
        try {
            Integer pid = projectService.createProject(pname);
            specService.newSpec(pid, sname, width, height);
            return Result.ok(null, "OK");
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.fail();
        }
    }
}
