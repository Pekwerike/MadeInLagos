package com.pekwerike.madeinlagos.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.pekwerike.madeinlagos.R
import com.pekwerike.madeinlagos.databinding.FragmentProductListBinding
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.ui.recyclerviewcomponents.ProductItemClickListener
import com.pekwerike.madeinlagos.ui.recyclerviewcomponents.ProductListRecyclerViewAdapter
import com.pekwerike.madeinlagos.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private val productListRecyclerViewAdapter = ProductListRecyclerViewAdapter(
        ProductItemClickListener {

        }
    )
    private lateinit var fragmentProductListBinding: FragmentProductListBinding
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeMainActivityViewModelLiveData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductListBinding = FragmentProductListBinding
            .inflate(inflater, container, false).apply {
                productListRecyclerView.adapter = productListRecyclerViewAdapter
                val gridSpanCount = if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                    3
                }else {
                    2
                }
                productListRecyclerView.layoutManager = StaggeredGridLayoutManager(
                    gridSpanCount,
                    StaggeredGridLayoutManager.VERTICAL
                )
                swipeToRefreshProductList.setOnRefreshListener {
                    mainActivityViewModel.refreshProductList()
                }
            }
        return fragmentProductListBinding.root
    }

    private fun observeMainActivityViewModelLiveData() {
        mainActivityViewModel.allProductsWithReviews.observe(this) {
            it?.let {
                productListRecyclerViewAdapter.submitList(it)
            }
        }
        mainActivityViewModel.networkResult.observe(this) {
            it?.let {
                fragmentProductListBinding.swipeToRefreshProductList.isRefreshing = false
            }

        }
    }
}