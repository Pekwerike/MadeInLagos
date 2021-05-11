package com.pekwerike.madeinlagos.di

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.network.ProductServiceAPI
import com.pekwerike.madeinlagos.network.impl.ProductService
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*

@InstallIn(SingletonComponent::class)
@Module
class NetworkDIModule{

    @Provides
    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun getHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }
        }
    }
}