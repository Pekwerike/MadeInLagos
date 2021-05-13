package com.pekwerike.madeinlagos.ui.recyclerviewcomponents

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pekwerike.madeinlagos.R
import com.pekwerike.madeinlagos.databinding.ProductItemLayoutBinding
import com.pekwerike.madeinlagos.model.Product

class ProductItemLayoutViewHolder(private val productItemLayoutBinding: ProductItemLayoutBinding) :
    RecyclerView.ViewHolder(productItemLayoutBinding.root) {

    fun bindProductData(
        product: Product,
        productItemClickListener: ProductItemClickListener
    ) {
        productItemLayoutBinding.apply {
            this.product = product

            Glide.with(productItemLayoutProductImageView)
                .load(product.productImageUrl)
                .into(productItemLayoutProductImageView)

            productItemLayoutCardView.transitionName = product.id

            productItemLayoutCardView.setOnClickListener {
                productItemClickListener.onClick(product, productItemLayoutCardView)
            }
            executePendingBindings()
        }
    }

    companion object {
        fun createViewHolder(parent: ViewGroup): ProductItemLayoutViewHolder {
            val layoutBinding = DataBindingUtil.inflate<ProductItemLayoutBinding>(
                LayoutInflater.from(parent.context),
                R.layout.product_item_layout,
                parent,
                false
            )
            return ProductItemLayoutViewHolder(layoutBinding)
        }
    }
}