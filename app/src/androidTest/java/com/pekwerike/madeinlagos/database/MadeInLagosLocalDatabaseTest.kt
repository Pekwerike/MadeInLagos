package com.pekwerike.madeinlagos.database

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.pekwerike.madeinlagos.utils.ProductDataSource
import com.squareup.moshi.Moshi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.sql.DataSource

@HiltAndroidTest
class MadeInLagosLocalDatabaseTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Inject
    lateinit var madeInLagosLocalDatabase: MadeInLagosLocalDatabase

    @Inject
    lateinit var productDataSource: ProductDataSource

    @Inject
    lateinit var moshi: Moshi

    private lateinit var productDao: ProductDAO

    @Before
    fun setUp() {
        hiltRule.inject()
        productDao = madeInLagosLocalDatabase.productDAO()
    }


    @Test
    fun testInsertProductInDatabase() {
        runBlocking {
            productDao.insertAllProducts(
                productDataSource.getProducts()
            )
        }
    }
}