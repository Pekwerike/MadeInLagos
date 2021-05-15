package com.pekwerike.madeinlagos.network

import android.util.Log
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
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
                    assert(networkResult.products.isNotEmpty())
                    networkResult.products.forEach {
                        Log.i("Products", it.id)
                    }
                }
                is NetworkResult.NoInternetConnection -> {
                    assert(false)
                    // display a log message
                    // Log.i("NettyResult", "No Internet Connection")
                }
                else -> {

                }
            }
        }
    }

    @Test
    fun createProduct() {
        runBlocking {
            val networkResult = productService.createProduct(
                Product(
                    id = "H1C12",
                    name = "product",
                    description = "description",
                    currency = "$",
                    price = 100
                )
            )
            when (networkResult) {
                is NetworkResult.Success.NoResponse -> {
                    assert(true)
                }
                is NetworkResult.HttpError -> {
                    assert(false)
                }
                is NetworkResult.NoInternetConnection -> {
                    assert(false)
                }
            }
        }
    }

    @Test
    fun getProductById() {
        runBlocking {
            // irrespective of the chosen id, mock server returns a product with the id of FI444
            when (val networkResult = productService.getProductById("FI444")) {
                is NetworkResult.Success.SingleProduct -> {
                    assertEquals("FI444", networkResult.product.id)
                }
                is NetworkResult.NoInternetConnection -> {
                    assert(false)
                }
                is NetworkResult.Success.NoResponse -> {
                    assertTrue(false)
                }
            }
        }
    }

    @Test
    fun updateProduct() {
        runBlocking {
            val networkResult = productService.updateProduct(
                "FI444",
                Product(
                    id = "FI444",
                    name = "updated product name",
                    description = "updated product description",
                    currency = "$",
                    price = 229
                )
            )
            when (networkResult) {
                is NetworkResult.Success.SingleProduct -> {
                    assertEquals("updated product name", networkResult.product.name)
                    assertEquals("updated product description", networkResult.product.description)
                }
                else -> {
                    assert(false)
                }
            }
        }
    }

    @Test
    fun deleteProduct() {
        runBlocking {
            when (productService.deleteProduct("HBC12")) {
                is NetworkResult.Success.DeletedProduct -> {
                    assert(true)
                }
                else -> {
                    assert(false)
                }
            }
        }
    }
}