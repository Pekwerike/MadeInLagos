package com.pekwerike.madeinlagos.ui.productdetail

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.pekwerike.madeinlagos.ui.productdetail.recyclerview.ProductReviewListRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PRODUCT_ID = "ExtraProductId"
    }

    private val productDetailActivityViewModel: ProductDetailActivityViewModel by viewModels()
    private lateinit var productDetailActivityBinding: ActivityProductDetailBinding
    private lateinit var productId: String
    private val productReviewListRecyclerViewAdapter = ProductReviewListRecyclerViewAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        postponeEnterTransition()
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = true
        super.onCreate(savedInstanceState)
        productId = intent.getStringExtra(EXTRA_PRODUCT_ID)!!
        productDetailActivityBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_product_detail
        )

        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            addTarget(productDetailActivityBinding.activityProductDetailContainer)
            duration = 600L
        }
        window.sharedElementReturnTransition = MaterialContainerTransform().apply {
            addTarget(productDetailActivityBinding.activityProductDetailContainer)
            duration = 600L
        }
        productDetailActivityViewModel.getProductWithReviewsFromCache(productId)
        productDetailActivityViewModel.getProductWithFreshReviewsById(productId)
        configureLayout()
        observeViewModelLiveData()
    }

    private fun configureLayout() {
        productDetailActivityBinding.apply {

            productDetailSwipeToRefreshLayout.setOnRefreshListener {
                productDetailActivityViewModel.getProductWithFreshReviewsById(productId)
            }
            activityProductDetailsToolbar.setNavigationOnClickListener {
                supportFinishAfterTransition()
            }
            productDetailPostProductReviewFab.setOnClickListener {
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    this@ProductDetailActivity,
                    it,
                    it.transitionName
                )
                startActivity(
                    Intent(
                        this@ProductDetailActivity,
                        ProductReviewActivity::class.java
                    ).apply {
                        putExtra(ProductReviewActivity.EXTRA_PRODUCT_ID, productId)
                    }, options.toBundle()
                )
            }
            productDetailProductReviewsRecyclerView.adapter = productReviewListRecyclerViewAdapter
        }
    }


    private fun observeViewModelLiveData() {
        productDetailActivityViewModel.apply {
            isConnectedToInternet.observe(this@ProductDetailActivity){
                if(it == false){
                    startPostponedEnterTransition()
                }
            }
            currentProductInDisplay.observe(this@ProductDetailActivity) {
                productDetailActivityBinding.apply {
                    productDetailSwipeToRefreshLayout.isRefreshing = false
                    product = it
                }
                Glide.with(this@ProductDetailActivity)
                    .load(it.productImageUrl)
                    .placeholder(R.drawable.ic_adidas_logo_wine)
                    .addListener(object:  RequestListener<Drawable> {
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

                productReviewListRecyclerViewAdapter.submitList(
                    it.productReviews
                )

            }
        }
    }
}