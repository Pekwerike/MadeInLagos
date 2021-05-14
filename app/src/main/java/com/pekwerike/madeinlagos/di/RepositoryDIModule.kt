package com.pekwerike.madeinlagos.di

import com.pekwerike.madeinlagos.repository.impl.MadeInLagosProductRepository
import com.pekwerike.madeinlagos.repository.MainRepositoryAPI
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryDIModule {

    @Binds
    abstract fun getMainRepositoryAPIImpl(
        madeInLagosProductRepository:
        MadeInLagosProductRepository
    ): MainRepositoryAPI
}