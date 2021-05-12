package com.pekwerike.madeinlagos.database

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before

@HiltAndroidTest
class MadeInLagosLocalDatabaseTest {

    private val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp(){
        hiltRule.inject()
    }
}