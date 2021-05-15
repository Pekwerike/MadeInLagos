package com.pekwerike.madeinlagos.network.impl

import android.util.Log
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.ProductReview
import com.pekwerike.madeinlagos.network.ProductReviewAPI
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import javax.inject.Inject

@HiltAndroidTest
class ProductReviewImplTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var productReviewAPIImpl: ProductReviewAPI

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun getProductReviews() {
        runBlocking {
            val networkResult = productReviewAPIImpl.getProductReviews("FI444")
//            assert(networkResult is NetworkResult.Success.ProductReviews)
            if (networkResult is NetworkResult.Success.ProductReviews) {
                val products = networkResult.productReviews
                products.forEach {
                    Log.i("ReviewsLaP", it.text)
                }
            }
        }
    }

    @Test
    fun postProductReview() {
        runBlocking {
            val networkResult = productReviewAPIImpl.postProductReview(
                ProductReview(
                    productId = "FI444",
                    rating = 5,
                    text = "From Ktorr"
                )
            )
        }
    }
}