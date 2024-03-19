package com.prototyping.backend.controller;

import com.prototyping.backend.entity.domain.Result;
import com.prototyping.backend.service.impl.SpecServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spec")
@Slf4j
public class SpecController {
    private SpecServiceImpl specService;

    @Autowired
    public void setSpecService(SpecServiceImpl specService) {
        this.specService = specService;
    }

    @PostMapping("/createSpec")
    public Result<Object> createSpec(@RequestBody Object data) {
        try {
            return Result.ok(specService.createSpec(data), "OK");
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.fail();
        }
    }

    @PostMapping("/deleteSpec")
    public Result<Object> deleteSpec(Integer id){
        try {
            specService.deleteSpec(id);
            return Result.ok(null, "ok");
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.fail();
        }
    }

    @PostMapping("/updateSpec")
    public Result<Object> updateSpec(@RequestBody Object data){
        try {
            specService.updateSpec(data);
            return Result.ok(null, "ok");
        } catch (Exception e){
            log.error(e.getMessage());
            return Result.fail();
        }
    }

    @GetMapping("/getAllSpecs")
    public Result<Object> getAllSpecs(Integer pid){
        try {
            if(!specService.isSpecExists(pid))
                return Result.fail(500,"项目不存在");
            else if(specService.getAllSpecs(pid) == null)
                return Result.fail(501,"项目已被逻辑删除");
            else
                return Result.ok(specService.getAllSpecs(pid), "ok");
        } catch (Exception e){
            log.error(e.getMessage());
            return Result.fail();
        }
    }

    @GetMapping("/getSpec")
    public Result<Object> getSpec(Integer id){
        try {
            if(specService.getSpec(id) == null){
                return Result.fail(500,"画布不存在");
            }
            else {
                return Result.ok(specService.getAllSpecs(id), "ok");
            }
        } catch (Exception e){
            log.error(e.getMessage());
            return Result.fail();
        }
    }

    @PostMapping("/createSpecWithParams")
    public Result<Object> createSpecWithParams(Integer pid, String name, Integer width, Integer height){
        try {
            specService.newSpec(pid,name,width,height);
            return Result.ok(null,"ok");
        }
        catch (Exception e){
            log.error(e.getMessage());
            return Result.fail();
        }
    }

}

