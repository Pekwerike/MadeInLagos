package com.pekwerike.madeinlagos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.pekwerike.madeinlagos.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    companion object {
        var isKeyBoardOpen: Boolean = false

        fun newKeyBoardState(isKeyBoardOpened : Boolean){
            isKeyBoardOpen = isKeyBoardOpened
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        newKeyBoardState(false)
    }
}