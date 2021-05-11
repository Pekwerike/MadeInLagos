package com.pekwerike.madeinlagos.network.impl

import android.util.Log
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.network.ProductServiceAPI
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import javax.inject.Inject

@HiltAndroidTest
class ProductServiceTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var productService: ProductServiceAPI

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getAllProduct() {
        runBlocking {
            when (val networkResult = productService.getAllProduct()) {
                is NetworkResult.Success.AllProducts -> {
                    Log.i("NetworkResult", networkResult.products.size.toString())
                }
                else -> {

                }
            }
        }
    }

    @Test
    fun createProduct() {
    }

    @Test
    fun getProductById() {
    }

    @Test
    fun updateProduct() {
    }

    @Test
    fun deleteProduct() {
    }
}