package com.pekwerike.madeinlagos

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MadeInLagos : Application() {

    override fun onTerminate() {

        super.onTerminate()
    }
}