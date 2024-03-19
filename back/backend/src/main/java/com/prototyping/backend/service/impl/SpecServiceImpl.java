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
import com.prototyping.backend.entity.Spec;
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

    @Autowired
    public void setSpecMapper(SpecMapper specMapper) {
        this.specMapper = specMapper;
    }

    @Override
    public Integer createSpec(Object data) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.convertValue(data, ObjectNode.class);
        System.out.println(node);

        Spec spec = new Spec();
        spec.setPid(node.get("pid").asInt());
        spec.setName(node.get("specName").asText());
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        spec.setCtime(timestamp);
        spec.setUtime(timestamp);
        spec.setIsDeleted(0);
        specMapper.insert(spec);
        return 0;
    }

    @Override
    public void deleteSpec(Integer id) {

        specMapper.deleteById(id);
    }

    @Override
    public void updateSpec(Object data) {
        ObjectMapper objectMapper = new ObjectMapper();
        //ObjectNode node = objectMapper.convertValue(data, ObjectNode.class);
        JsonNode node = objectMapper.convertValue(data, JsonNode.class);
        System.out.println(node);

        int id = node.get("id").asInt();

        JsonNode cells_node = node.get("cells");
        System.out.println(cells_node);
        String json_string = cells_node.toString();
        System.out.println(json_string);
        String json_data = JSON.toJSONString(json_string);
        System.out.println(json_data);
        String name;
        LambdaUpdateWrapper<Spec> lambdaUpdateWrapper = new LambdaUpdateWrapper();
        lambdaUpdateWrapper.eq(Spec::getId, id);
        if (node.get("name") != null) {
            name = node.get("name").asText();
            lambdaUpdateWrapper.set(Spec::getName, name);
        }
        lambdaUpdateWrapper.set(Spec::getJsonData, json_data);
        specMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public JSONArray getAllSpecs(Integer pid) {
        LambdaQueryWrapper<Spec> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(pid != null, Spec::getPid, pid);
        List<Spec> specList = specMapper.selectList(lambdaQueryWrapper);
        JSONArray res = new JSONArray();
        if (!specList.isEmpty()) {
            for (Spec spec : specList) {
                String json_data = spec.getJsonData();
                int length = json_data.length();
                json_data = json_data.substring(1, length - 1);
                json_data = json_data.replaceAll("\\\\", "");
                JSONArray jsonArray = JSON.parseArray(json_data);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", spec.getId());
                jsonObject.put("pid", spec.getPid());
                jsonObject.put("name", spec.getName());
                jsonObject.put("ctime", spec.getCtime());
                jsonObject.put("utime", spec.getUtime());
                jsonObject.put("json_data", jsonArray);
                res.add(jsonObject);
            }
            return res;
        }
        else return null;
    }

    @Override
    public JSONObject getSpec(Integer id) {
        Spec spec = specMapper.selectById(id);

        String json_data = spec.getJsonData();
        int length = json_data.length();
        json_data = json_data.substring(1, length - 1);
        json_data = json_data.replaceAll("\\\\", "");
        JSONArray jsonArray = JSON.parseArray(json_data);

        /*ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonObject = objectMapper.createObjectNode();*/
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", spec.getId());
        jsonObject.put("pid", spec.getPid());
        jsonObject.put("name", spec.getName());
        jsonObject.put("ctime", spec.getCtime());
        jsonObject.put("utime", spec.getUtime());
        jsonObject.put("json_data", jsonArray);
        System.out.println(jsonObject);

        //JSONObject jsonObject = JSONObject.parseObject(spec.getJsonData());
        //System.out.println(jsonObject);
        //JsonNode result = objectMapper.convertValue(jsonObject,JsonNode.class);
        return jsonObject;
    }

    @Override
    public JSONArray test() {
        String str = specMapper.selectById(2).getJsonData();
        int length = str.length();
        String test = str.substring(1, length - 1);
        System.out.println("原字符串" + test);
        test = test.replaceAll("\\\\", "");
        System.out.println("现字符串" + test);
        JSONArray jsonArray = JSON.parseArray(test);
        System.out.println(jsonArray);
        return jsonArray;
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
