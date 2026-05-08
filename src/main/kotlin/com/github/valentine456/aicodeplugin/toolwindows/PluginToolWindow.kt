package com.github.valentine456.aicodeplugin.toolwindows

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.content.ContentFactory
import java.awt.BorderLayout

class PluginToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = PluginToolWindowPanel()
        val content = ContentFactory.getInstance().createContent(panel, "", false)
        toolWindow.contentManager.addContent(content)
    }
}

class PluginToolWindowPanel : JBPanel<PluginToolWindowPanel>(BorderLayout()) {
    private val textArea = JBTextArea("Waiting for analysis...").apply {
        lineWrap = true
        wrapStyleWord = true
        isEditable = false
    }

    init {
        add(JBScrollPane(textArea), BorderLayout.CENTER)
    }

    fun updateText(text: String) {
        textArea.text = text
        textArea.caretPosition = 0
    }
}