package com.pekwerike.madeinlagos.di

import com.pekwerike.madeinlagos.database.MadeInLagosLocalDatabase
import com.pekwerike.madeinlagos.network.ProductReviewAPI
import com.pekwerike.madeinlagos.network.ProductServiceAPI
import com.pekwerike.madeinlagos.network.impl.ProductReviewImpl
import com.pekwerike.madeinlagos.network.impl.ProductServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class ProductAPIsDIModule {

    @Binds
    abstract fun getProductServiceAPIImplementation(productService: ProductServiceImpl) : ProductServiceAPI

    @Binds
    abstract fun getProductReviewAPIImplementation(productReviewImpl: ProductReviewImpl): ProductReviewAPI
}