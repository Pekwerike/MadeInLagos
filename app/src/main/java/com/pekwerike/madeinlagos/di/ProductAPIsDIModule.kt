package com.pekwerike.madeinlagos.di

import com.pekwerike.madeinlagos.network.ProductReviewAPI
import com.pekwerike.madeinlagos.network.ProductServiceAPI
import com.pekwerike.madeinlagos.network.retrofit.ProductServiceRetrofitImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class ProductAPIsDIModule {

    @Binds
    abstract fun getProductServiceAPIImplementation(productService: ProductServiceRetrofitImpl) : ProductServiceAPI

    @Binds
    abstract fun getProductReviewAPIImplementation(productReviewImpl: ProductServiceRetrofitImpl): ProductReviewAPI
}