import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.intellij.platform.gradle.tasks.RunIdeTask
import java.util.Properties

val envProps: Map<String, String> = rootProject.file(".env").let { file ->
    if (!file.exists()) emptyMap()
    else file.readLines()
        .filter { it.isNotBlank() && !it.startsWith("#") }
        .mapNotNull { line ->
            val stripped = line.removePrefix("export").trim()
            val idx = stripped.indexOf('=')
            if (idx > 0) stripped.substring(0, idx).trim() to stripped.substring(idx + 1).trim()
            else null
        }
        .toMap()
}
fun env(key: String): String = envProps[key] ?: System.getenv(key) ?: ""

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.3.0"
    id("org.jetbrains.intellij.platform")
    id("org.jetbrains.changelog")
}

tasks.withType<RunIdeTask> {
    envProps.forEach { (k, v) -> systemProperty(k, v) }
}

tasks.withType<Test> {
    envProps.forEach { (k, v) -> systemProperty(k, v) }
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlin" && requested.name.startsWith("kotlin-stdlib")) {
            useVersion("2.1.20")
        }
    }
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    implementation("ai.koog:koog-agents:0.7.1") {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-core")
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-core-jvm")
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-jdk8")
    }
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        intellijIdea("2025.2.6.2")
        testFramework(TestFrameworkType.Platform)
    }
}
