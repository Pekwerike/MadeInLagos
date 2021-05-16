package com.pekwerike.madeinlagos.di


import com.pekwerike.madeinlagos.network.ProductServiceAPI
import com.pekwerike.madeinlagos.network.retrofit.ProductServiceRetrofitAPI
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkDIModule {

    @Provides
    @Singleton
    fun getProductServiceRetrofitAPI(retrofit: Retrofit): ProductServiceRetrofitAPI {
        return retrofit.create(ProductServiceRetrofitAPI::class.java)
    }

    @Provides
    @Singleton
    fun getRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(ProductServiceAPI.BASE_URL)
            .build()
    }


    @Provides
    @Singleton
    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

}