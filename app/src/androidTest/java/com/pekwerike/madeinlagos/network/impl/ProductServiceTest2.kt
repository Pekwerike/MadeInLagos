package com.pekwerike.madeinlagos.network.impl

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.network.ProductServiceAPI
import com.pekwerike.madeinlagos.utils.ProductDataSource
import com.squareup.moshi.Moshi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
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

@HiltAndroidTest
@RunWith(MockitoJUnitRunner::class)
class ProductServiceTest2 {

    @Mock
    lateinit var productServiceAPI: ProductServiceAPI

    @Inject
    lateinit var productDataSource: ProductDataSource

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

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
                    productDataSource.getProducts()
                )
            )

            val networkResult = productServiceAPI.getAllProduct()
            assert(networkResult is NetworkResult.Success.AllProducts)
            assertEquals(
                "FI444",
                (networkResult as NetworkResult.Success.AllProducts).products[0].id
            )
        }
    }


    @Test
    fun createProduct() {
        runBlocking {
            val product = Product(
                id = "H1C12",
                name = "product",
                description = "description",
                currency = "$",
                price = 100
            )
            `when`(productServiceAPI.createProduct(product)).thenReturn(
                NetworkResult.Success.SingleProduct(product)
            )
            when (val networkResult = productServiceAPI.createProduct(product)) {
                is NetworkResult.Success.SingleProduct -> {
                    assertEquals("H1C12", networkResult.product.id)
                }
                is NetworkResult.HttpError -> {
                    assert(false)
                }
                is NetworkResult.NoInternetConnection -> {
                    assert(false)
                }
                else -> {
                    assert(false)
                }
            }
        }
    }

    @ExperimentalStdlibApi
    @Test
    fun getProductById() {
        runBlocking {
            `when`(productServiceAPI.getProductById("F1444")).thenReturn(
                NetworkResult.Success.SingleProduct(
                    productDataSource.getProducts()[0]
                )
            )
            // irrespective of the chosen id, mock server returns a product with the id of FI444
            when (val networkResult = productServiceAPI.getProductById("FI444")) {
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
            val product = Product(
                id = "FI444",
                name = "updated product name",
                description = "updated product description",
                currency = "$",
                price = 229
            )
            `when`(productServiceAPI.updateProduct("FI444", product)).thenReturn(
                NetworkResult.Success.SingleProduct(product)
            )
            val networkResult = productServiceAPI.updateProduct(
                "FI444",
                product
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
            `when`(productServiceAPI.deleteProduct("HBC12")).thenReturn(
                NetworkResult.Success.DeletedProduct
            )
            when (productServiceAPI.deleteProduct("HBC12")) {
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