package com.pekwerike.madeinlagos.ui.productdetail.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pekwerike.madeinlagos.model.ui.ProductDetailRecyclerViewItemModel

class ProductDetailRecyclerViewAdapter :
    ListAdapter<ProductDetailRecyclerViewItemModel, RecyclerView.ViewHolder>(
        ProductDetailRecyclerViewAdapterDiffUtil
    ) {

    enum class ProductDetailRecyclerViewItemType(val value: Int) {
        PRODUCT_ITEM(1),
        PRODUCT_REVIEW_ITEM(2),
        PRODUCT_REVIEW_HEADER(3)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ProductDetailRecyclerViewItemModel.ProductItem -> {
                ProductDetailRecyclerViewItemType.PRODUCT_ITEM.value
            }
            is ProductDetailRecyclerViewItemModel.ProductReviewHeader -> {
                ProductDetailRecyclerViewItemType.PRODUCT_REVIEW_HEADER.value
            }
            is ProductDetailRecyclerViewItemModel.ProductReviewItem -> {
                ProductDetailRecyclerViewItemType.PRODUCT_REVIEW_ITEM.value
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ProductDetailRecyclerViewItemType.PRODUCT_ITEM.value -> {
                ProductDetailHeaderItemLayoutViewHolder.createViewHolder(parent)
            }
            ProductDetailRecyclerViewItemType.PRODUCT_REVIEW_HEADER.value -> {
                ProductReviewItemHeaderLayoutViewHolder.createViewHolder(parent)
            }
            ProductDetailRecyclerViewItemType.PRODUCT_REVIEW_ITEM.value -> {
                ProductReviewItemLayoutViewHolder.createViewHolder(parent)
            }
            else -> {
                ProductReviewItemLayoutViewHolder.createViewHolder(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductReviewItemLayoutViewHolder -> {
                holder.bindProductReview(
                    productReview = (getItem(position) as
                            ProductDetailRecyclerViewItemModel.ProductReviewItem).productReview
                )
            }
            is ProductReviewItemHeaderLayoutViewHolder -> {
            }
            is ProductDetailHeaderItemLayoutViewHolder -> {
                holder.bindProductDetail(
                    product = (getItem(position) as
                            ProductDetailRecyclerViewItemModel.ProductItem).product
                )
            }
        }
    }

    object ProductDetailRecyclerViewAdapterDiffUtil :
        DiffUtil.ItemCallback<ProductDetailRecyclerViewItemModel>() {
        override fun areItemsTheSame(
            oldItem: ProductDetailRecyclerViewItemModel,
            newItem: ProductDetailRecyclerViewItemModel
        ): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(
            oldItem: ProductDetailRecyclerViewItemModel,
            newItem: ProductDetailRecyclerViewItemModel
        ): Boolean {
            return oldItem == newItem
        }

    }


}

