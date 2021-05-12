package com.pekwerike.madeinlagos.testdi

import android.content.Context
import androidx.room.Room
import com.pekwerike.madeinlagos.database.MadeInLagosLocalDatabase
import com.pekwerike.madeinlagos.di.DatabaseDIModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseDIModule::class]
)
@Module
class TestDatabaseDIModule {

    @Provides
    fun getMadeInLagosLocalDatabaseForTest(@ApplicationContext context: Context):
            MadeInLagosLocalDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            MadeInLagosLocalDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }
}