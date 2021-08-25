package com.samkortekaas.codam.header.services

import com.intellij.openapi.project.Project
import com.samkortekaas.codam.header.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
