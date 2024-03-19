package com.prototyping.backend.controller;

import com.prototyping.backend.entity.Project;
import com.prototyping.backend.entity.domain.Result;
import com.prototyping.backend.service.impl.ProjectServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@Slf4j
public class MainController {
    private ProjectServiceImpl projectService;

    @Autowired
    public void setCreateProject(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/test")
    public String test() {
        return "get test";
    }

    // 不要在 controller 里使用 mapper
    // 使用 spring 推荐的自动注入方式
    // service 方法的异常在 controller 里捕获
    @PostMapping("/createProjectWithOneSpec")
    public Result<Object> createProjectWithOneSpec(@RequestBody Object data) {
        try {
            return Result.ok(projectService.createWithOneSpec(data), "OK");
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.fail();
        }

    }

}
