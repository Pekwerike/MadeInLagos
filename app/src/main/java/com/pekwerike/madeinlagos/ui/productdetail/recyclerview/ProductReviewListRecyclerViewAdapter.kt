package com.pekwerike.madeinlagos.ui.productdetail.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pekwerike.madeinlagos.model.ProductReview

class ProductReviewListRecyclerViewAdapter() :
    ListAdapter<ProductReview, RecyclerView.ViewHolder>(ProductReviewListRecyclerViewDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductReviewItemLayoutViewHolder.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductReviewItemLayoutViewHolder) {
            holder.bindProductReview(getItem(position))
        }
    }
}