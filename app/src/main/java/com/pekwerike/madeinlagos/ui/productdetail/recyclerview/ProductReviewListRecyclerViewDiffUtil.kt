package com.pekwerike.madeinlagos.ui.productdetail.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.pekwerike.madeinlagos.model.ProductReview

object ProductReviewListRecyclerViewDiffUtil : DiffUtil.ItemCallback<ProductReview>() {
    override fun areItemsTheSame(oldItem: ProductReview, newItem: ProductReview): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: ProductReview, newItem: ProductReview): Boolean {
        return oldItem == newItem
    }
}