package com.pekwerike.madeinlagos.ui.productdetail.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pekwerike.madeinlagos.databinding.ProductReviewsItemHeaderLayoutBinding

class ProductReviewItemHeaderLayoutViewHolder(
    private val productReviewsItemHeaderLayoutBinding:
    ProductReviewsItemHeaderLayoutBinding
) : RecyclerView.ViewHolder(productReviewsItemHeaderLayoutBinding.root) {


    companion object {
        fun createViewHolder(parent: ViewGroup): ProductReviewItemHeaderLayoutViewHolder {
            val layoutBinding = ProductReviewsItemHeaderLayoutBinding.inflate(
                LayoutInflater.from(parent.context)
            )
            return ProductReviewItemHeaderLayoutViewHolder(layoutBinding)
        }
    }
}