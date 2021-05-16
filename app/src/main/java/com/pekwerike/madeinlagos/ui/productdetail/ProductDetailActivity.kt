package com.pekwerike.madeinlagos.ui.productdetail

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.pekwerike.madeinlagos.R
import com.pekwerike.madeinlagos.databinding.ActivityProductDetailBinding
import com.pekwerike.madeinlagos.model.ui.ProductDetailRecyclerViewItemModel
import com.pekwerike.madeinlagos.ui.productdetail.recyclerview.ProductDetailRecyclerViewAdapter
import com.pekwerike.madeinlagos.ui.productreview.ProductReviewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PRODUCT_ID = "ExtraProductId"
        const val REFRESH_REVIEW_LIST_EXTRA = "RefreshReviewList"
    }

    private val intentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data?.getBooleanExtra(REFRESH_REVIEW_LIST_EXTRA, false) == true) {
                    productDetailActivityViewModel.getProductWithFreshReviewsById(productId)
                }
            }
        }
    private val productDetailRecyclerViewAdapter = ProductDetailRecyclerViewAdapter()
    private val productDetailActivityViewModel: ProductDetailActivityViewModel by viewModels()
    private lateinit var productDetailActivityBinding: ActivityProductDetailBinding
    private lateinit var productId: String
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

            productDetailRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@ProductDetailActivity, LinearLayoutManager.VERTICAL, false)
                adapter = productDetailRecyclerViewAdapter
            }
            swipeToRefreshProductDetail.setOnRefreshListener {
                productDetailActivityViewModel.getProductWithFreshReviewsById(productId)
            }
            activityProductDetailsToolbar.setNavigationOnClickListener {
                supportFinishAfterTransition()
            }

            productDetailPostProductReviewFab.setOnClickListener {
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@ProductDetailActivity,
                    it,
                    it.transitionName
                )

                intentLauncher.launch(
                    Intent(
                        this@ProductDetailActivity,
                        ProductReviewActivity::class.java
                    ).apply {
                        putExtra(ProductReviewActivity.EXTRA_PRODUCT_ID, productId)
                    }, options
                )
            }
        }
    }


    private fun observeViewModelLiveData() {
        productDetailActivityViewModel.apply {
            currentProductInDisplay.observe(this@ProductDetailActivity) {
                productDetailActivityBinding.swipeToRefreshProductDetail.isRefreshing = false
                CoroutineScope(Dispatchers.Default).launch {
                    val productDetailItemList = mutableListOf<ProductDetailRecyclerViewItemModel>()
                    productDetailItemList.add(0, ProductDetailRecyclerViewItemModel.ProductItem(it))
                    productDetailItemList.add(
                        1,
                        ProductDetailRecyclerViewItemModel.ProductReviewHeader
                    )
                    productDetailItemList.addAll(it.productReviews.map { productReview ->
                        ProductDetailRecyclerViewItemModel.ProductReviewItem(productReview)
                    })
                    withContext(Dispatchers.Main) {
                        productDetailRecyclerViewAdapter.submitList(
                            productDetailItemList
                        )
                        startPostponedEnterTransition()
                    }
                }
            }
        }
    }
}