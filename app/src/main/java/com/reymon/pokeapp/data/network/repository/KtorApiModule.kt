package com.reymon.pokeapp.data.network.repository


import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.statement.request
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation

import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorApiModule {

    fun getKtorHttpClient()  = HttpClient(Android) {
        expectSuccess = true
        engine {
            connectTimeout = 1000
            socketTimeout = 1000
        }
        // Logging
        install(Logging) {
            level = LogLevel.ALL
        }
        // JSON
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                useAlternativeNames = true
                ignoreUnknownKeys = true
                encodeDefaults = false
            })
        }
        // Timeout
        install(HttpTimeout) {
            requestTimeoutMillis = 1000
            connectTimeoutMillis = 1000
            socketTimeoutMillis = 1000
        }
        // Response observer
        install(ResponseObserver) {
            onResponse { response ->
                Log.d("Observer KTOR", response.request.content.status.toString())
            }
        }
        // Apply to all requests
        // Add headers if your endpoint requires
        defaultRequest {
            // Content Type
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
        }
    }


}
