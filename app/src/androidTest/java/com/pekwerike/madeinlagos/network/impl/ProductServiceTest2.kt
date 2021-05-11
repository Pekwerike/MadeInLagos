package com.pekwerike.madeinlagos.network.impl

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.pekwerike.madeinlagos.FakeDataSource
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.network.ProductServiceAPI
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
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
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@HiltAndroidTest
@RunWith(MockitoJUnitRunner::class)
class ProductServiceTest2 {

    @Mock
    lateinit var productServiceAPI: ProductServiceAPI
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var moshi: Moshi

    @InternalAPI
    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
    }

    @ExperimentalStdlibApi
    @Test
    fun getAllProduct() {
        runBlocking {
            `when`(productServiceAPI.getAllProduct()).thenReturn(
                NetworkResult.Success.AllProducts(
                    moshi.adapter<List<Product>>().fromJson(FakeDataSource.getProducts(context))
                        ?: listOf()
                )
            )

            val networkResult = productServiceAPI.getAllProduct()
            assert(networkResult is NetworkResult.Success.AllProducts)
            assertEquals("FI444", (networkResult as NetworkResult.Success.AllProducts).products[0].id)
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