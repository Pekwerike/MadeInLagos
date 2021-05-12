package com.pekwerike.madeinlagos.di

import com.pekwerike.madeinlagos.network.ProductServiceAPI
import com.pekwerike.madeinlagos.network.impl.ProductService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class ProductAPIsDIModule {

    @Binds
    abstract fun getProductServiceAPIImplementation(productService: ProductServiceImpl) : ProductServiceAPI

    @Binds
    abstract fun getProductReviewAPIImplementation(productReviewImpl: ProductReviewImpl): ProductReviewAPI
}