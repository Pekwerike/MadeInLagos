package com.pekwerike.madeinlagos.model.ui

import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.model.ProductReview

sealed class ProductDetailRecyclerViewItemModel {
    abstract val itemId: String

    data class ProductItem(val product: Product) : ProductDetailRecyclerViewItemModel() {
        override val itemId: String
            get() = product.productImageUrl
    }

    object ProductReviewHeader : ProductDetailRecyclerViewItemModel() {
        override val itemId: String
            get() = "ProductReviewHeader"
    }

    data class ProductReviewItem(val productReview: ProductReview) :
        ProductDetailRecyclerViewItemModel() {
        override val itemId: String
            get() = productReview.hashCode().toString()
    }
}
