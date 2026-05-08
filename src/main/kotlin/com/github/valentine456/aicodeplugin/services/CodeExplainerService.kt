package com.github.valentine456.aicodeplugin.services

import com.github.valentine456.aicodeplugin.toolwindows.PluginToolWindowPanel
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Service(Service.Level.PROJECT)
class CodeExplainerService(
    private val project: Project,
    private val scope: CoroutineScope,
) {
    var panel: PluginToolWindowPanel? = null

    private var currentJob: Job? = null

    fun explainSnippet(snippet: String) {
        currentJob?.cancel()

        val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("AICodePlugin") ?: return
        toolWindow.show {
            val target = panel ?: return@show
            target.updateText("Loading...")

            currentJob = scope.launch {
                try {
                    var first = true
                    service<LLMProviderService>().explainCodeSnippet(snippet).collect { chunk ->
                        ApplicationManager
                            .getApplication()
                            .invokeLater {
                            if (first) {
                                target.updateText(chunk)
                                first = false
                            } else {
                                target.appendText(chunk)
                            }
                        }
                    }
                } catch (e: Exception) {
                    ApplicationManager.getApplication().invokeLater {
                        target.updateText("Error: ${e.message ?: e.javaClass.simpleName}")
                    }
                }
            }
        }
    }
}
