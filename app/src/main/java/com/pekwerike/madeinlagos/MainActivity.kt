package com.pekwerike.madeinlagos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

interface OnBackPressed {
    fun backPressed(): Boolean
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        var onBackPressedImpl: OnBackPressed? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }


    override fun onBackPressed() {
        if (onBackPressedImpl != null) {
            val closeApp = onBackPressedImpl!!.backPressed()
            if (closeApp) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }
}