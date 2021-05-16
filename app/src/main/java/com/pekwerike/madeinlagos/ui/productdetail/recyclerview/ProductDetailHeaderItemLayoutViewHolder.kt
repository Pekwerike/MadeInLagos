package com.pekwerike.madeinlagos.ui.productdetail.recyclerview

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pekwerike.madeinlagos.R
import com.pekwerike.madeinlagos.databinding.ProductDetailRecyclerViewProductHeaderItemLayoutBinding
import com.pekwerike.madeinlagos.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailHeaderItemLayoutViewHolder(
    private val productDetailRecyclerViewProductHeaderItemLayoutBinding:
    ProductDetailRecyclerViewProductHeaderItemLayoutBinding
) : RecyclerView.ViewHolder(productDetailRecyclerViewProductHeaderItemLayoutBinding.root) {

    fun bindProductDetail(product: Product) {
        productDetailRecyclerViewProductHeaderItemLayoutBinding.apply {
            this.product = product

            Glide.with(root)
                .load(product.productImageUrl)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        CoroutineScope(Dispatchers.Main).launch {
                            Glide.with(root)
                                .load(R.drawable.ic_adidas_logo_wine)
                                .into(productDetailRecyclerviewProductImageView)
                        }
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                })
                .into(productDetailRecyclerviewProductImageView)
        }
    }

    companion object {
        fun createViewHolder(parent: ViewGroup):
                ProductDetailHeaderItemLayoutViewHolder {
            val layoutBinding =
                DataBindingUtil
                    .inflate<ProductDetailRecyclerViewProductHeaderItemLayoutBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.product_detail_recycler_view_product_header_item_layout,
                        parent,
                        false
                    )
            return ProductDetailHeaderItemLayoutViewHolder(layoutBinding)
        }
    }
}