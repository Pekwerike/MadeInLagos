package com.pekwerike.madeinlagos

import android.app.Application
import android.util.Log
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.network.ProductReviewAPI
import com.pekwerike.madeinlagos.network.ProductServiceAPI
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
class ProductServiceTest {

    @Mock
    lateinit var productServiceAPI: ProductServiceAPI


    @InternalAPI
    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getAllProduct(){
        runBlocking {
            `when`(productServiceAPI.getAllProduct()).thenReturn(
                NetworkResult.Success.AllProducts(

                )
            )
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