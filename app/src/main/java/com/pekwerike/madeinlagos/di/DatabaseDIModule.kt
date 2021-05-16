package com.pekwerike.madeinlagos.di

import android.content.Context
import com.pekwerike.madeinlagos.database.MadeInLagosLocalDatabase
import com.pekwerike.madeinlagos.database.ProductDAO
import com.pekwerike.madeinlagos.database.ProductReviewDAO
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseDIModule {

    @Provides
    fun getProductDao(madeInLagosLocalDatabase: MadeInLagosLocalDatabase): ProductDAO {
        return madeInLagosLocalDatabase.productDAO()
    }

    @Provides
    fun getProductReviewDao(madeInLagosLocalDatabase: MadeInLagosLocalDatabase): ProductReviewDAO {
        return madeInLagosLocalDatabase.productReviewDAO()
    }

    @Provides
    @Singleton
    fun getMadeInLagosLocalDatabase(@ApplicationContext context: Context): MadeInLagosLocalDatabase {
        return MadeInLagosLocalDatabase.getMadeInLagosLocalDatabaseInstance(
            context = context
        )
    }
}
