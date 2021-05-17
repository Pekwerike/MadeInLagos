package com.pekwerike.madeinlagos.mappers

import com.pekwerike.madeinlagos.FakeDataSource
import com.pekwerike.madeinlagos.utils.ProductDataSource
import org.junit.Test

import org.junit.Assert.*

class ProductMappersKtTest {
    private val productList = FakeDataSource.productList
    private val productReviewList = FakeDataSource.productReviewList

    @Test
    fun test_productListToProductEntityList() {
        val productEntityList = productList.productListToProductEntityList()
        assertEquals("F323", productEntityList[0].productId)
        assertEquals(productList.size, productEntityList.size)
    }


    @Test
    fun test_productToProductEntity() {
        val product = productList[0]
        val productEntity = product.productToProductEntity()
        assertEquals(product.id, productEntity.productId)
        assertEquals(product.productImageUrl, productEntity.productImageUrl)
        assertEquals(product.name, productEntity.name)
        assertEquals(product.description, productEntity.description)
        assertEquals(product.price, productEntity.price)
    }

    @Test
    fun test_productReviewListToProductReviewEntityList() {
        val productReviewEntityList = productReviewList.productReviewToProductReviewEntityList()
        assertEquals(productReviewEntityList.size, productReviewList.size)
    }

    @Test
    fun test_productReviewToProductReviewEntity(){
        val productReview = productReviewList[0]
        val productReviewEntity = productReview.productReviewToProductReviewEntity()
        assertEquals(productReview.productId, productReviewEntity.productId)
        assertEquals(productReview.locale, productReviewEntity.locale)
        assertEquals(productReview.rating, productReviewEntity.rating)
        assertEquals(productReview.text, productReviewEntity.text)
    }


}