package com.prototyping.backend.service;

import com.prototyping.backend.entity.Project;

import java.util.List;

public interface IProjectService {
    Integer createWithOneSpec(Object data);
    List<Project> getAllProjects();
    List<Project> getDeletedProjects();
    void deleteProjectById(Integer id);
    void renameProjectById(Integer id, String name);
    Integer createProject(String name);
    public void restoreProject(Integer id);
    public Integer queryNumOfSpecs(Integer id);
}
