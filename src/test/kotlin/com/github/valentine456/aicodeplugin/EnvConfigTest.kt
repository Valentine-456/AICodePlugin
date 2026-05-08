package com.github.valentine456.aicodeplugin

import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Test

class EnvConfigTest {

    @Test
    fun `GOOGLE_API_KEY is loaded from env`() {
        val key = System.getProperty("GOOGLE_API_KEY") ?: System.getenv("GOOGLE_API_KEY")
        assertNotNull(
            "GOOGLE_API_KEY must be set in .env (forwarded by Gradle as a system property) or as a real OS environment variable. See example.env for the expected format.",
            key
        )
        assertFalse("GOOGLE_API_KEY must not be blank", key!!.isBlank())
    }
}
