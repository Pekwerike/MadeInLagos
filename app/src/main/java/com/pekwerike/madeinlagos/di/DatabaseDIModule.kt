package com.pekwerike.madeinlagos.di

import android.content.Context
import com.pekwerike.madeinlagos.database.MadeInLagosLocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class DatabaseDIModule {

    @Provides
    fun getMadeInLagosLocalDatabase(@ApplicationContext context: Context): MadeInLagosLocalDatabase {
        return MadeInLagosLocalDatabase.getMadeInLagosLocalDatabaseInstance(
            context = context
        )
    }
}