package com.github.skrtks.codamheader.services

import com.github.skrtks.codamheader.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
