package net.ucoz.testcompose.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.http.ContentType
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKtorHttpClient(): HttpClient {
//        val client = HttpClient(Android){
//
//            install(JsonFeature){
//
//                serializer= KotlinxSerializer(kotlinx.serialization.json.Json{
//                    prettyPrint= true
//                    isLenient= true
//                    ignoreUnknownKeys= true
//                })
//
//                engine {
//                    connectTimeout= 50000
//                    socketTimeout= 50000
//                }
//            }
//
//            install(Logging) {
//                logger = object : Logger {
//                    override fun log(message: String) {
//                        Log.v("Logger Ktor =>", message)
//                    }
//
//                }
//                level = LogLevel.ALL
//            }
//
//            install(ResponseObserver) {
//                onResponse { response ->
//                    Log.d("HTTP status:", "${response.status.value}")
//                }
//            }
//
//            install(DefaultRequest) {
//                header(HttpHeaders.ContentType, ContentType.Application.Json)
//            }
//        }

        val client =
            HttpClient(Android) {
                // Logging
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Log.v("http log: ", message)
                        }
                    }
                    level = LogLevel.ALL
                }
                // JSON
                install(JsonFeature) {
//                    serializer = KotlinxSerializer(json)

//                    serializer = KotlinxSerializer()

                    val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
                    serializer = KotlinxSerializer(json)
                    acceptContentTypes = acceptContentTypes + ContentType.Any
                }
                // Timeout
                install(HttpTimeout) {
                    requestTimeoutMillis = 50000L
                    connectTimeoutMillis = 50000L
                    socketTimeoutMillis = 50000L
                }
//                // Apply to all requests
//                defaultRequest {
//                    // Parameter("api_key", "some_api_key")
//                    // Content Type
//                    if (method != HttpMethod.Get) contentType(ContentType.Application.Json)
//                    accept(ContentType.Application.Json)
//                }
            }

        return client
    }

    private val json = kotlinx.serialization.json.Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
    }


}