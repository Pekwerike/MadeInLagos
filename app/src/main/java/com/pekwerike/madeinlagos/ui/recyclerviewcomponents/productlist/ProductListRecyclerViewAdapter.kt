package com.pekwerike.madeinlagos.ui.recyclerviewcomponents.productlist

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pekwerike.madeinlagos.model.Product

class ProductListRecyclerViewAdapter(
    private val productItemClickListener:
    ProductItemClickListener
) : ListAdapter<Product, RecyclerView.ViewHolder>(ProductListRecyclerViewAdapterDiffUtil) {

    companion object {
        enum class ProductLayoutItemType(val value: Int) {
            PRIME(1),
            EVEN(0)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 != 0 && position % 3 != 0
            || position == 3 || position == 2
        ) {
            ProductLayoutItemType.PRIME.value
        } else {
            ProductLayoutItemType.EVEN.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ProductLayoutItemType.EVEN.value -> ProductItemLayoutViewHolder.createViewHolder(parent)
            ProductLayoutItemType.PRIME.value -> ProductItemLayoutTwoViewHolder.createViewHolder(
                parent
            )
            else -> ProductItemLayoutTwoViewHolder.createViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductItemLayoutViewHolder -> {
                holder.bindProductData(getItem(position), productItemClickListener)
            }
            is ProductItemLayoutTwoViewHolder -> {
                holder.bindProductData(getItem(position), productItemClickListener)
            }
        }
    }
}


object ProductListRecyclerViewAdapterDiffUtil : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}