package com.pekwerike.madeinlagos.ui.productdetail.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pekwerike.madeinlagos.R
import com.pekwerike.madeinlagos.databinding.ProductReviewItemLayoutBinding
import com.pekwerike.madeinlagos.model.ProductReview

class ProductReviewItemLayoutViewHolder(
    private val productReviewItemLayoutBinding:
    ProductReviewItemLayoutBinding
) :
    RecyclerView.ViewHolder(productReviewItemLayoutBinding.root) {

    fun bindProductReview(productReview: ProductReview) {
        productReviewItemLayoutBinding.apply {
            this.productReview = productReview
            executePendingBindings()
        }
    }

    companion object {
        fun createViewHolder(parent: ViewGroup): ProductReviewItemLayoutViewHolder {
            val layoutBinding = DataBindingUtil.inflate<ProductReviewItemLayoutBinding>(
                LayoutInflater.from(parent.context),
                R.layout.product_review_item_layout,
                parent,
                false
            )

            return ProductReviewItemLayoutViewHolder(layoutBinding)
        }
    }
}