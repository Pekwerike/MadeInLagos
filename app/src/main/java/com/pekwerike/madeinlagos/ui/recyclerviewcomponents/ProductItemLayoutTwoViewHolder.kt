package com.pekwerike.madeinlagos.ui.recyclerviewcomponents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pekwerike.madeinlagos.R
import com.pekwerike.madeinlagos.databinding.ProductItemLayoutBinding
import com.pekwerike.madeinlagos.databinding.ProductItemLayoutTwoBinding
import com.pekwerike.madeinlagos.model.Product

class ProductItemLayoutTwoViewHolder(private val productItemLayoutTwoBinding: ProductItemLayoutTwoBinding) :
    RecyclerView.ViewHolder(productItemLayoutTwoBinding.root) {

    fun bindProductData(product: Product, productItemClickListener: ProductItemClickListener) {
        productItemLayoutTwoBinding.apply {
            this.product = product

            Glide.with(productItemLayoutTwoProductImageView)
                .load(product.productImageUrl)
                .placeholder(ContextCompat.getDrawable(root.context, R.drawable.ic_adidas_seeklogo))
                .into(productItemLayoutTwoProductImageView)

            productItemLayoutTwoCardView.transitionName = product.id

            productItemLayoutTwoCardView.setOnClickListener {
                productItemClickListener.onClick(product, it)
            }
            executePendingBindings()
        }
    }

    companion object {
        fun createViewHolder(parent: ViewGroup): ProductItemLayoutTwoViewHolder {
            val layoutBinding = DataBindingUtil.inflate<ProductItemLayoutTwoBinding>(
                LayoutInflater.from(parent.context),
                R.layout.product_item_layout_two,
                parent,
                false
            )
            return ProductItemLayoutTwoViewHolder(layoutBinding)
        }
    }
}