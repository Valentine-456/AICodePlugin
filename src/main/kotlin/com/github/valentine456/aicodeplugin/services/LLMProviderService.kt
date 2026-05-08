package com.github.valentine456.aicodeplugin.services

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.llms.all.simpleGoogleAIExecutor
import com.intellij.openapi.components.Service

@Service
class LLMProviderService {
    val LLMExecutor = simpleGoogleAIExecutor(
        System.getProperty("GOOGLE_API_KEY") ?: System.getenv("GOOGLE_API_KEY") ?: error("GOOGLE_API_KEY not set")
    )
    val agent = AIAgent(
        promptExecutor = LLMExecutor,
        llmModel = GoogleModels.Gemini2_5Flash
    )

    public suspend fun explainCodeSnippet(snippet: String): String {
        val result = agent.run("Hello! How can you help me?")
        return result
    }
}