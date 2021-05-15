package com.pekwerike.madeinlagos.ui.productdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
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
        super.onCreate(savedInstanceState)
        productDetailActivityBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_product_detail
        )
        productId = intent.getStringExtra(EXTRA_PRODUCT_ID)!!
        productDetailActivityViewModel.getProductById(productId)
        configureLayout()
        observeViewModelLiveData()
    }

    private fun configureLayout() {
        productDetailActivityBinding.apply {
            activityProductDetailsToolbar.setNavigationOnClickListener {
                finish()
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
                    .into(productDetailActivityBinding.productDetailProductImageView)
            }
        }
    }
}