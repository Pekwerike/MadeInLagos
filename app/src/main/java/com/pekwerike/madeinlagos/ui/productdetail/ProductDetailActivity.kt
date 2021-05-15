package com.pekwerike.madeinlagos.ui.productdetail

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.pekwerike.madeinlagos.R
import com.pekwerike.madeinlagos.databinding.ActivityProductDetailBinding
import com.pekwerike.madeinlagos.ui.productreview.ProductReviewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PRODUCT_ID = "ExtraProductId"
    }

    private val productDetailActivityViewModel: ProductDetailActivityViewModel by viewModels()
    private lateinit var productDetailActivityBinding: ActivityProductDetailBinding
    private lateinit var productId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        postponeEnterTransition()
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        super.onCreate(savedInstanceState)
        productId = intent.getStringExtra(EXTRA_PRODUCT_ID)!!
        productDetailActivityBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_product_detail
        )
        productDetailActivityBinding.activityProductDetailContainer.transitionName = productId
        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            addTarget(productDetailActivityBinding.activityProductDetailContainer)
            duration = 600L
        }
        window.sharedElementReturnTransition = MaterialContainerTransform().apply {
            addTarget(productDetailActivityBinding.activityProductDetailContainer)
            duration = 600L
        }
        productDetailActivityViewModel.getProductById(productId)
        configureLayout()
        observeViewModelLiveData()
    }

    private fun configureLayout() {
        productDetailActivityBinding.apply {
            activityProductDetailsToolbar.setNavigationOnClickListener {
                supportFinishAfterTransition()
            }
            productDetailPostProductReviewFab.setOnClickListener {
                startActivity(Intent(
                    this@ProductDetailActivity,
                    ProductReviewActivity::class.java
                ).apply {
                    putExtra(ProductReviewActivity.EXTRA_PRODUCT_ID, productId)
                })
            }
        }
    }


    private fun observeViewModelLiveData() {
        productDetailActivityViewModel.apply {
            currentProductInDisplay.observe(this@ProductDetailActivity) {
                productDetailActivityBinding.product = it
                Glide.with(this@ProductDetailActivity)
                    .load(it.productImageUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            startPostponedEnterTransition()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            startPostponedEnterTransition()
                            return false
                        }
                    })
                    .into(productDetailActivityBinding.productDetailProductImageView)
            }
        }
    }
}