package com.pekwerike.madeinlagos.ui.productreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.pekwerike.madeinlagos.R
import com.pekwerike.madeinlagos.databinding.ActivityProductReviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductReviewActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PRODUCT_ID = "ProductIDExtra"
    }

    private lateinit var activityProductReviewBinding: ActivityProductReviewBinding
    private val productReviewViewModel: ProductReviewViewModel by viewModels()
    private lateinit var productId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityProductReviewBinding = ActivityProductReviewBinding.inflate(layoutInflater)
        setContentView(activityProductReviewBinding.root)
        productId = intent.getStringExtra(EXTRA_PRODUCT_ID)!!
        configureLayout()
        observeViewModelLiveData()
    }

    private fun observeViewModelLiveData() {
        productReviewViewModel.apply {
            reviewPostNetworkResult.observe(this@ProductReviewActivity) {

            }
        }
    }

    private fun configureLayout() {
        activityProductReviewBinding.apply {
            productReviewToolbar.setOnMenuItemClickListener {
                if (it.itemId == R.id.post_review_menu_item) {
                    val userRating = productReviewRatingBar.rating
                    val userReview = productReviewEditText.text.toString()
                    productReviewViewModel.postProductReview(
                        productId,
                        userRating.toInt(),
                        userReview
                    )
                    true
                } else false
            }
        }
    }
}