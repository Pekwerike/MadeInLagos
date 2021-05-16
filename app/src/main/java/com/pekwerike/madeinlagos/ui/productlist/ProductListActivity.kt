package com.pekwerike.madeinlagos.ui.productlist

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.pekwerike.madeinlagos.R
import com.pekwerike.madeinlagos.databinding.ActivityProductListBinding
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.ui.productdetail.ProductDetailActivity
import com.pekwerike.madeinlagos.ui.productlist.recyclerview.ProductItemClickListener
import com.pekwerike.madeinlagos.ui.productlist.recyclerview.ProductListRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListActivity : AppCompatActivity() {
    private lateinit var productListActivityBinding: ActivityProductListBinding
    private val productListViewModel: ProductListViewModel by viewModels()
    private val productListRecyclerViewAdapter = ProductListRecyclerViewAdapter(
        ProductItemClickListener { product, clickedView ->
            // only use shared element transitioning when the app is in portrait mode
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    clickedView,
                    product.id
                )
                Intent(this, ProductDetailActivity::class.java).also {
                    it.putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, product.id)
                    startActivity(it, options.toBundle())
                }
            } else {
                Intent(this, ProductDetailActivity::class.java).also {
                    it.putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, product.id)
                    startActivity(it)
                }
            }
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        postponeEnterTransition()
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = true
        super.onCreate(savedInstanceState)
        productListActivityBinding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(productListActivityBinding.root)
        setUpLayoutComponents()
        observeViewModelLiveData()
    }

    private fun observeViewModelLiveData() {
        productListViewModel.apply {
            productList.observe(this@ProductListActivity) {
                productListRecyclerViewAdapter.submitList(it)
            }
            productFetchNetworkResult.observe(this@ProductListActivity) {
                productListActivityBinding.swipeToRefreshProductList.isRefreshing = false
                when (it) {
                    is NetworkResult.Success -> {
                        productListActivityBinding.apply {
                            productListUserLabel.animate().alpha(0f)
                            initialFetchingDataFromServerProgressIndicator.animate().alpha(0f)
                        }
                    }
                    is NetworkResult.HttpError -> {
                        if (productList.value?.isEmpty() == true) {
                            productListActivityBinding.apply {
                                initialFetchingDataFromServerProgressIndicator.animate().alpha(0f)
                                productListUserLabel.apply {
                                    animate().alpha(1f)
                                    text = getString(R.string.server_downtime)
                                }
                            }
                        }
                    }
                    is NetworkResult.NoInternetConnection -> {
                        if (productList.value?.isEmpty() == true) {
                            productListActivityBinding.apply {
                                initialFetchingDataFromServerProgressIndicator.animate().alpha(0f)
                                productListUserLabel.apply {
                                    animate().alpha(1f)
                                    text = getString(R.string.no_internet_connection_label)
                                }
                            }
                        }
                    }
                    is NetworkResult.FetchingDataFromServer -> {
                        productListActivityBinding.initialFetchingDataFromServerProgressIndicator.animate()
                            .alpha(1f)
                    }
                }
            }
        }
    }

    private fun setUpLayoutComponents() {
        productListActivityBinding.apply {
            productListRecyclerView.apply {
                val gridSpanCount =
                    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        3
                    } else {
                        2
                    }
                layoutManager = StaggeredGridLayoutManager(
                    gridSpanCount,
                    StaggeredGridLayoutManager.VERTICAL
                )
                adapter = productListRecyclerViewAdapter
                post {
                    startPostponedEnterTransition()
                }
            }
            productListSearchBar.apply {
                addTextChangedListener {
                    productListViewModel.filterProductList(it.toString())
                }
                setOnEditorActionListener { _, actionId, _ ->
                    return@setOnEditorActionListener when (actionId) {
                        EditorInfo.IME_ACTION_SEARCH -> {

                            true
                        }
                        else -> false
                    }
                }
                setOnFocusChangeListener { _, hasFocus ->

                }
            }
            swipeToRefreshProductList.setOnRefreshListener {
                productListViewModel.fetchNewProductsFromNetwork()
            }
        }
    }

    override fun onBackPressed() {
        if (productListActivityBinding.productListSearchBar.text.toString().isNotEmpty()) {
            productListActivityBinding.productListSearchBar.apply {
                setText("")
                clearFocus()
            }
        } else {
            super.onBackPressed()
        }
    }
}