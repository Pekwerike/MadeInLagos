package com.pekwerike.madeinlagos.ui.fragment

import android.app.SharedElementCallback
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.TransitionInflater
import com.google.android.material.transition.MaterialContainerTransform
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
        ProductItemClickListener { product, clickedView ->
            mainActivityViewModel.selectProduct(product)
            val navigationExtras =
                FragmentNavigatorExtras(clickedView to product.id)

            findNavController()
                .navigate(
                    ProductListFragmentDirections.actionProductListFragment2ToProductDetailFragment2(product.id),
                    navigationExtras
                )
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
    ): View {

        fragmentProductListBinding = FragmentProductListBinding
            .inflate(inflater, container, false).apply {
                productListRecyclerView.apply {
                    adapter = productListRecyclerViewAdapter
                    postponeEnterTransition()
                    viewTreeObserver.addOnPreDrawListener {
                        startPostponedEnterTransition()
                        true
                    }
                }

                val gridSpanCount =
                    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        3
                    } else {
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
        mainActivityViewModel.selectedProduct.observe(this) {
            it?.let {

            }
        }
    }
}