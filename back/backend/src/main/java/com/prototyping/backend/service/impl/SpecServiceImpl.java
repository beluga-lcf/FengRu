package com.prototyping.backend.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.prototyping.backend.entity.Project;
import com.prototyping.backend.entity.Spec;
import com.prototyping.backend.mapper.ProjectMapper;
import com.prototyping.backend.mapper.SpecMapper;
import com.prototyping.backend.service.ISpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SpecServiceImpl extends ServiceImpl<SpecMapper, Spec> implements ISpecService {

    private SpecMapper specMapper;
    private ProjectMapper projectMapper;

    @Autowired
    public void setSpecMapper(SpecMapper specMapper) {
        this.specMapper = specMapper;
    }
    @Autowired
    public void setProjectMapper(ProjectMapper projectMapper){
        this.projectMapper = projectMapper;
    }
    @Override
    public Integer createSpec(Object data) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.convertValue(data, ObjectNode.class);
        System.out.println(node);

        Spec spec = new Spec();
        spec.setPid(node.get("pid").asInt());
        spec.setName(node.get("specName").asText());
        spec.setHeight(node.get("height").asInt());
        spec.setWidth(node.get("width").asInt());
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        spec.setCtime(timestamp);
        spec.setUtime(timestamp);
        spec.setIsDeleted(0);
        specMapper.insert(spec);
        return spec.getId();
    }

    @Override
    public Integer deleteSpec(Object data) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.convertValue(data, ObjectNode.class);
        System.out.println(node);
        if(node.get("id") != null){
            int id = node.get("id").asInt();
            specMapper.deleteById(id);
            return 0;
        }
        else{
            return 1;
        }
    }

    @Override
    public Integer updateSpec(Object data) {
        ObjectMapper objectMapper = new ObjectMapper();
        //ObjectNode node = objectMapper.convertValue(data, ObjectNode.class);
        JsonNode node = objectMapper.convertValue(data, JsonNode.class);
        System.out.println(node);

        if(node.get("id") == null){
            return 1;
        }
        int id = node.get("id").asInt();
        Spec spec = specMapper.selectById(id);
        if(spec == null){
            return 2;
        }

        JsonNode cells_node;
        String name;
        int height;
        int width;
        LambdaUpdateWrapper<Spec> lambdaUpdateWrapper = new LambdaUpdateWrapper();
        lambdaUpdateWrapper.eq(Spec::getId, id);
        if(node.get("name") != null) {
            name = node.get("name").asText();
            lambdaUpdateWrapper.set(Spec::getName, name);
        }
        if(node.get("cells") != null){
            cells_node = node.get("cells");
            //System.out.println(cells_node);
            String json_string = cells_node.toString();
            //System.out.println(json_string);
            String json_data = JSON.toJSONString(json_string);
            //System.out.println(json_data);
            lambdaUpdateWrapper.set(Spec::getJsonData, json_data);
        }
        if(node.get("height") != null){
            height = node.get("height").asInt();
            lambdaUpdateWrapper.set(Spec::getHeight, height);
        }
        if(node.get("width") != null){
            width = node.get("width").asInt();
            lambdaUpdateWrapper.set(Spec::getWidth, width);
        }
        specMapper.update(null, lambdaUpdateWrapper);
        return 0;
    }

    @Override
    public JSONObject getAllSpecs(Integer pid) {
        LambdaQueryWrapper<Spec> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(pid != null, Spec::getPid, pid);
        Project project = projectMapper.selectById(pid);
        JSONObject res = new JSONObject();
        if(project == null){
            return null;
        }
        //如果存在项目
        res.put("project_name", project.getName());
        List<Spec> specList = specMapper.selectList(lambdaQueryWrapper);
        JSONArray spec_res = new JSONArray();
        if (!specList.isEmpty()) {
            for (Spec spec : specList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", spec.getId());
                jsonObject.put("pid", spec.getPid());
                jsonObject.put("name", spec.getName());
                jsonObject.put("height", spec.getHeight());
                jsonObject.put("width", spec.getWidth());
                jsonObject.put("ctime", spec.getCtime());
                jsonObject.put("utime", spec.getUtime());
                if(spec.getJsonData() != null){
                    String json_data = spec.getJsonData();
                    int length = json_data.length();
                    json_data = json_data.substring(1, length - 1);
                    json_data = json_data.replaceAll("\\\\", "");
                    JSONArray jsonArray = JSON.parseArray(json_data);
                    jsonObject.put("json_data", jsonArray);
                }
                else {
                    jsonObject.put("json_data", null);
                }
                spec_res.add(jsonObject);
            }
            res.put("spec_data", spec_res);
        }
        else res.put("spec_data", null);
        return res;
    }

    @Override
    public JSONObject getSpec(Integer id) {
        Spec spec = specMapper.selectById(id);
        if(spec == null){
            return null;
        }

        /*ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonObject = objectMapper.createObjectNode();*/
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", spec.getId());
        jsonObject.put("pid", spec.getPid());
        jsonObject.put("name", spec.getName());
        jsonObject.put("height", spec.getHeight());
        jsonObject.put("width", spec.getWidth());
        jsonObject.put("ctime", spec.getCtime());
        jsonObject.put("utime", spec.getUtime());
        if(spec.getJsonData() != null){
            String json_data = spec.getJsonData();
            int length = json_data.length();
            json_data = json_data.substring(1, length - 1);
            json_data = json_data.replaceAll("\\\\", "");
            JSONArray jsonArray = JSON.parseArray(json_data);
            jsonObject.put("json_data", jsonArray);
        }
        else {
            jsonObject.put("json_data", null);
        }
        //System.out.println(jsonObject);

        //JSONObject jsonObject = JSONObject.parseObject(spec.getJsonData());
        //System.out.println(jsonObject);
        //JsonNode result = objectMapper.convertValue(jsonObject,JsonNode.class);
        return jsonObject;
    }

    @Override
    public void newSpec(Integer pid, String specName, Integer width, Integer height) {
        Spec spec = new Spec();
        spec.setPid(pid);
        spec.setName(specName);
        spec.setWidth(width);
        spec.setHeight(height);
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        spec.setCtime(timestamp);
        spec.setUtime(timestamp);
        specMapper.insert(spec);
    }

    @Override
    public boolean isSpecExists(Integer pid){
         if(specMapper.findSpecs(pid)==0){
             return false;
         }
         else return true;
    }
}
