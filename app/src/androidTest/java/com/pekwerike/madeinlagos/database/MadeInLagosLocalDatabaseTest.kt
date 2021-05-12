package com.pekwerike.madeinlagos.database

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.pekwerike.madeinlagos.mappers.*
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.model.ProductRating
import com.pekwerike.madeinlagos.model.ProductReview
import com.pekwerike.madeinlagos.utils.ProductDataSource
import com.squareup.moshi.Moshi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
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
    private lateinit var productReviewDao: ProductReviewDAO

    @Before
    fun setUp() {
        hiltRule.inject()
        productDao = madeInLagosLocalDatabase.productDAO()
        productReviewDao = madeInLagosLocalDatabase.productReviewDAO()
    }


    @ExperimentalStdlibApi
    @Test
    fun testInsertProductListIntoDatabase() {
        runBlocking {
            productDao.insertAllProducts(
                productDataSource.getProducts().toProductEntityList()
            )
            assertEquals(23, productDao.getAllProducts().first().size)
            assertEquals(23, productDao.getAllProductWithReviews().first().size)
        }
    }

    @ExperimentalStdlibApi
    @Test
    fun test_getProductWithReviewsByProductId() {
        runBlocking {
            productDao.insertAllProducts(
                productDataSource.getProducts().toProductEntityList()
            )
            val product: Product =
                productDao.getProductWithReviewsByProductId(productId = "FI444").first()
                    .productWithReviewsToProduct()
            assertEquals(product.id, "FI444")
            assertEquals(product.name, "updated product name")
            assert(product.productReviews.isEmpty())
        }
    }

    @ExperimentalStdlibApi
    @Test
    fun test_insertProductReview() {
        runBlocking {
            productDao.insertAllProducts(
                productDataSource.getProducts().toProductEntityList()
            )
            productReviewDao.insertProductReview(
                ProductReview(
                    productId = "FI444",
                    locale = "en-US",
                    rating = ProductRating.FIVE.ratingValue,
                    text = "Yo, beautiful product, Thank you Adidas"
                ).productReviewToProductReviewEntity()
            )
            val product = productDao.getProductWithReviewsByProductId("FI444").first().productWithReviewsToProduct()
            assert(product.productReviews.isNotEmpty())
            assertEquals("Yo, beautiful product, Thank you Adidas", product.productReviews[0].text)
        }
    }
}