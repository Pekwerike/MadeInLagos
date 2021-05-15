package com.pekwerike.madeinlagos.ui.productdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.pekwerike.madeinlagos.R
import com.pekwerike.madeinlagos.databinding.ActivityProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PRODUCT_ID = "ExtraProductId"
    }

    private val productDetailActivityViewModel: ProductDetailActivityViewModel by viewModels()
    private lateinit var productDetailActivityBinding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productDetailActivityBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_product_detail
        )
        val productId = intent.getStringExtra(EXTRA_PRODUCT_ID)
        val product = productDetailActivityViewModel.getProductById(productId!!)

    }


    private fun observeViewModelLiveData() {
        productDetailActivityViewModel.apply {
            currentProductInDisplay.observe(this@ProductDetailActivity) {
                productDetailActivityBinding.product = it
            }
        }
    }
}