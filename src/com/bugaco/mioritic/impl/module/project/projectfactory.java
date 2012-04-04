package com.bugaco.mioritic.impl.module.project;

import com.bugaco.mioritic.model.module.project.Project;

/**
 * <p>Title: Mioritic</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: bugaco</p>
 *
 * @author Ivica Ceraj
 * @version 1.0
 */
public class ProjectFactory implements com.bugaco.mioritic.model.module.project.ProjectFactory {
    public ProjectFactory() {
    }

    public Project createProject() {
        return new com.bugaco.mioritic.impl.module.project.Project();
    }

    public void disposeProject(Project project) {
    }


}
