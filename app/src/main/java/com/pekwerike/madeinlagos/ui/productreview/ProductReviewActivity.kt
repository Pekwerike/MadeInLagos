package com.pekwerike.madeinlagos.ui.productreview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.pekwerike.madeinlagos.R
import com.pekwerike.madeinlagos.databinding.ActivityProductReviewBinding
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.ui.productdetail.ProductDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_product_review.*
import javax.inject.Inject

@AndroidEntryPoint
class ProductReviewActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PRODUCT_ID = "ProductIDExtra"
    }

    @Inject
    lateinit var inputMethodManager: InputMethodManager

    private lateinit var activityProductReviewBinding: ActivityProductReviewBinding
    private val productReviewViewModel: ProductReviewViewModel by viewModels()

    private lateinit var productId: String
    override fun onCreate(savedInstanceState: Bundle?) {

        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = true
        super.onCreate(savedInstanceState)
        // use view binding to inflate the activity layout
        activityProductReviewBinding = ActivityProductReviewBinding.inflate(layoutInflater)
        setContentView(activityProductReviewBinding.root)

        // specify activity shared element transition properties
        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            addTarget(activityProductReviewBinding.activityProductReviewContainer)
            duration = 600L
        }
        window.sharedElementReturnTransition = MaterialContainerTransform().apply {
            addTarget(activityProductReviewBinding.activityProductReviewContainer)
            duration = 600L
        }

        productId = intent.getStringExtra(EXTRA_PRODUCT_ID)!!
        configureLayout()
        observeViewModelLiveData()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showLoadingScrim(shouldShowScrim: Boolean = true) {
        activityProductReviewBinding.apply {
            if (shouldShowScrim) {
                window.statusBarColor = ContextCompat.getColor(
                    this@ProductReviewActivity,
                    android.R.color.darker_gray
                )
                productReviewPostingReviewProgressIndicator.animate().alpha(1f)
                productReviewPostingReviewScrim.animate().alpha(0.5f)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = ContextCompat.getColor(
                        this@ProductReviewActivity,
                        R.color.on_background_color
                    )
                }
                productReviewPostingReviewProgressIndicator.animate().alpha(0f)
                productReviewPostingReviewScrim.animate().alpha(0f)
            }

            // disable or enable the other layout click abilities
            productReviewEditText.setOnTouchListener { _, _ -> shouldShowScrim }
            productReviewRatingBar.setOnTouchListener { _, _ -> shouldShowScrim }
            productReviewToolbar.setOnTouchListener { _, _ -> shouldShowScrim }
            productReviewToolbar.setNavigationOnClickListener {
                if (shouldShowScrim) {
                    // do nothing
                } else {
                    setResult(Activity.RESULT_OK, Intent().apply {
                        putExtra(ProductDetailActivity.REFRESH_REVIEW_LIST_EXTRA, true)
                    })
                    supportFinishAfterTransition()
                }
            }
            productReviewToolbar.setOnMenuItemClickListener {
                if (shouldShowScrim) {
                    // do nothing
                } else {
                    if (it.itemId == R.id.post_review_menu_item) {
                        val userRating = productReviewRatingBar.rating
                        val userReview = productReviewEditText.text.toString()
                        productReviewViewModel.postProductReview(
                            productId,
                            userRating.toInt(),
                            userReview
                        )
                    }
                }
                true
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun observeViewModelLiveData() {
        productReviewViewModel.apply {
            reviewPostNetworkResult.observe(this@ProductReviewActivity) {
                when (it) {
                    is NetworkResult.Loading -> {
                        showLoadingScrim(true)
                        activityProductReviewBinding
                            .productReviewPostStateUserInfoLabelTextview
                            .animate()
                            .alpha(0f)
                    }
                    is NetworkResult.Success.SingleProductReview -> {
                        showLoadingScrim(false)
                        activityProductReviewBinding
                            .productReviewPostStateUserInfoLabelTextview
                            .animate()
                            .alpha(0f)

                        setResult(Activity.RESULT_OK, Intent().apply {
                            putExtra(ProductDetailActivity.REFRESH_REVIEW_LIST_EXTRA, true)
                        })
                        supportFinishAfterTransition()
                    }
                    is NetworkResult.NoInternetConnection -> {
                        showLoadingScrim(false)
                        activityProductReviewBinding.productReviewPostStateUserInfoLabelTextview.apply {
                            text = getString(R.string.no_internet_connection_label)
                            animate().alpha(1f)
                        }
                    }
                    is NetworkResult.HttpError -> {
                        showLoadingScrim(false)
                        activityProductReviewBinding.productReviewPostStateUserInfoLabelTextview.apply {
                            text = getString(R.string.server_downtime)
                            animate().alpha(1f)
                        }
                    }
                    else -> {
                        showLoadingScrim(false)
                    }
                }
            }
        }
    }

    private fun configureLayout() {
        activityProductReviewBinding.apply {

            productReviewToolbar.apply {
                setOnMenuItemClickListener {
                    if (it.itemId == R.id.post_review_menu_item) {
                        inputMethodManager.hideSoftInputFromWindow(
                            windowToken,
                            0
                        )
                        val userRating = productReviewRatingBar.rating
                        val userReview = productReviewEditText.text.toString()
                        if (userRating != 0f) {
                            productReviewViewModel.postProductReview(
                                productId,
                                userRating.toInt(),
                                userReview
                            )
                        }
                        true
                    } else false
                }
                setNavigationOnClickListener {
                    setResult(Activity.RESULT_OK, Intent().apply {
                        putExtra(ProductDetailActivity.REFRESH_REVIEW_LIST_EXTRA, true)
                    })
                    supportFinishAfterTransition()
                }
            }
        }
    }
}