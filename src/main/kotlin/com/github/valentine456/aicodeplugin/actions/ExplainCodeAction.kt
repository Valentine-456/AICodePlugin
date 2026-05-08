package com.github.valentine456.aicodeplugin.actions

import com.github.valentine456.aicodeplugin.services.CodeExplainerService
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.components.service

class ExplainCodeAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val editor = event.getData(CommonDataKeys.EDITOR) ?: return
        val project = event.project ?: return
        val snippet = editor.selectionModel.selectedText ?: editor.document.text
        project.service<CodeExplainerService>().explainSnippet(snippet)
    }
}
