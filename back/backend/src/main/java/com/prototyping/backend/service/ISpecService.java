package com.prototyping.backend.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;
import com.prototyping.backend.entity.Spec;

import java.util.List;

public interface ISpecService extends IService<Spec> {
    Integer createSpec(Object data);
    void deleteSpec(Integer id);
    void updateSpec(Object data);
    JSONObject getAllSpecs(Integer pid);
    JSONObject getSpec(Integer id);
    void newSpec(Integer pid, String specName, Integer width, Integer height);
    boolean isSpecExists(Integer pid);
}
