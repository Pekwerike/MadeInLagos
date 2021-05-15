package com.pekwerike.madeinlagos.ui.productlist

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.pekwerike.madeinlagos.databinding.ActivityProductListBinding
import com.pekwerike.madeinlagos.ui.productdetail.ProductDetailActivity
import com.pekwerike.madeinlagos.ui.recyclerviewcomponents.productlist.ProductItemClickListener
import com.pekwerike.madeinlagos.ui.recyclerviewcomponents.productlist.ProductListRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListActivity : AppCompatActivity() {
    private lateinit var productListActivityBinding: ActivityProductListBinding
    private val productListViewModel: ProductListViewModel by viewModels()
    private val productListRecyclerViewAdapter = ProductListRecyclerViewAdapter(
        ProductItemClickListener { product, clickedView ->
            // only use shared element transitioning when the app is in portrait mode
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Intent(this, ProductDetailActivity::class.java).also {
                    it.putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, product.id)
                    startActivity(it)
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
                productListViewModel.getAllProducts()
            }
        }
    }
}