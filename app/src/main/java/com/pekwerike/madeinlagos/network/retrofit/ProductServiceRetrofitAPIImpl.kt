package com.pekwerike.madeinlagos.network.retrofit

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductServiceRetrofitAPIImpl @Inject constructor(private val retrofit: Retrofit) {
    fun productServiceApi(): ProductServiceRetrofitAPI {
        return retrofit.create(ProductServiceRetrofitAPI::class.java)
    }
}