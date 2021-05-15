package com.pekwerike.madeinlagos.di

import android.content.Context
import android.os.Build
import android.view.inputmethod.InputMethodManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(ActivityComponent::class)
@Module
class AndroidComponentsDIModule {

    @Provides
    fun getInputManager(@ApplicationContext context: Context): InputMethodManager {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getSystemService(InputMethodManager::class.java) as InputMethodManager
        } else {
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        }
    }
}