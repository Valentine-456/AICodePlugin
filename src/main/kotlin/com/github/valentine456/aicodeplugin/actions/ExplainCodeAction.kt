package com.github.valentine456.aicodeplugin.actions

import com.github.valentine456.aicodeplugin.services.LLMProviderService
import com.github.valentine456.aicodeplugin.toolwindows.PluginToolWindowPanel
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.wm.ToolWindowManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.launch

class ExplainCodeAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val editor: Editor = event.getData(CommonDataKeys.EDITOR) ?: return
        val project = event.project ?: return
        val llmProviderService = service<LLMProviderService>()

        val document = editor.document
        val selectionModel = editor.selectionModel
        val selectedText = selectionModel.selectedText ?: document.text

        val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("AICodePlugin")
        toolWindow?.show {
            val panel = toolWindow.contentManager.getContent(0)?.component as? PluginToolWindowPanel
            panel?.updateText("Loading...")

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    llmProviderService.explainCodeSnippet(selectedText)
                        .withIndex()
                        .collect { (i, chunk) ->
                            ApplicationManager.getApplication().invokeLater {
                                if (i == 0) panel?.updateText(chunk)
                                else panel?.appendText(chunk)
                            }
                        }
                } catch (e: Error) {
                    ApplicationManager.getApplication().invokeLater {
                        panel?.updateText("Error: ${e.message}")
                    }
                }
            }
        }
    }
}