package com.github.valentine456.aicodeplugin.services

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.dsl.prompt
import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.llms.all.simpleGoogleAIExecutor
import ai.koog.prompt.streaming.filterTextOnly
import com.intellij.openapi.components.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.ExperimentalTime

@Service
class LLMProviderService {
    val llmExecutor = simpleGoogleAIExecutor(
        System.getProperty("GOOGLE_API_KEY") ?: System.getenv("GOOGLE_API_KEY") ?: error("GOOGLE_API_KEY not set")
    )
    val agent = AIAgent(
        promptExecutor = llmExecutor,
        llmModel = GoogleModels.Gemini2_5Flash,
        temperature = 0.2,
        systemPrompt = "You are an AI code assistant and you work with code. Answer using short responses. Do not overwhelm the user.",
    )

    @OptIn(ExperimentalTime::class)
    fun explainCodeSnippet(snippet: String): Flow<String> = flow {
        val query = prompt("ExplainCodeSnippet_${System.currentTimeMillis()}") {
            system("You are an AI code assistant and you work with code. Answer using short responses. Do not overwhelm the user.")
            user("Explain the following code: $snippet")
        }

        llmExecutor.executeStreaming(prompt = query, GoogleModels.Gemini2_5Flash)
            .filterTextOnly()
            .collect { chunk -> emit(chunk) }
    }
}