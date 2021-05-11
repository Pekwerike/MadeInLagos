package com.pekwerike.madeinlagos.testdi

import android.content.Context
import androidx.core.content.ContextCompat
import com.pekwerike.madeinlagos.R
import com.pekwerike.madeinlagos.di.NetworkDIModule
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.network.ProductServiceAPI
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.http.*


/*
 TestNetworkDIModule replaces the NetworkDIModule with a mock implementation
 of the HttpClient class for stable network interaction
*/


@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkDIModule::class]
)
@Module
class TestNetworkDIModule {

    @Provides
    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun getMoshiProductListAdapter(moshi: Moshi): JsonAdapter<List<Product>> {
        return moshi.adapter(Types.newParameterizedType(List::class.java, Product::class.java))
    }

    @Provides
    fun getHttpClient(@ApplicationContext context: Context): HttpClient {
        return HttpClient(MockEngine) {
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }
            engine {
                addHandler { request: HttpRequestData ->
                    when (request.url.fullPath) {
                        ProductServiceAPI.ALL_PRODUCTS_URL -> {
                            respond(
                                content = context.getString(R.string.all_products_http_response),
                                status = HttpStatusCode.OK
                            )
                        }
                        else -> {
                            respond(content = "I salute una", status = HttpStatusCode.OK)
                        }
                    }
                }
            }
        }
    }
}