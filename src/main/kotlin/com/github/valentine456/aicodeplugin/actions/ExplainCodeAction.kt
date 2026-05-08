package com.github.valentine456.aicodeplugin.actions

import com.github.valentine456.aicodeplugin.services.LLMProviderService
import com.github.valentine456.aicodeplugin.toolwindows.PluginToolWindowPanel
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.wm.ToolWindowManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExplainCodeAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val llmProviderService = service<LLMProviderService>()

        val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("AICodePlugin")
        toolWindow?.show {
            val panel = toolWindow.contentManager.getContent(0)?.component as? PluginToolWindowPanel
            panel?.updateText("Loading...")

            CoroutineScope(Dispatchers.IO).launch {
                val result = llmProviderService.explainCodeSnippet("")
                ApplicationManager.getApplication().invokeLater {
                    panel?.updateText(result)
                }
            }
        }
    }
}