package com.pekwerike.madeinlagos.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.pekwerike.madeinlagos.MainActivity
import com.pekwerike.madeinlagos.OnBackPressed
import com.pekwerike.madeinlagos.R
import com.pekwerike.madeinlagos.databinding.FragmentProductListBinding
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.ui.recyclerviewcomponents.ProductItemClickListener
import com.pekwerike.madeinlagos.ui.recyclerviewcomponents.ProductListRecyclerViewAdapter
import com.pekwerike.madeinlagos.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private var isKeyBoardOpen = false

    @Inject
    lateinit var inputMethodManager: InputMethodManager

    private val productListRecyclerViewAdapter = ProductListRecyclerViewAdapter(
        ProductItemClickListener { product, clickedView ->
            mainActivityViewModel.selectProduct(product)

            // only use shared element transitioning when the app is in portrait mode
            if (requireContext().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                val navigationExtras =
                    FragmentNavigatorExtras(clickedView to product.id)

                findNavController()
                    .navigate(
                        ProductListFragmentDirections.actionProductListFragment2ToProductDetailFragment2(),
                        navigationExtras
                    )
            } else {
                findNavController()
                    .navigate(
                        ProductListFragmentDirections.actionProductListFragment2ToProductDetailFragment2(
                            product.id
                        )
                    )
            }
        }
    )
    private lateinit var fragmentProductListBinding: FragmentProductListBinding
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivity.onBackPressedImpl = object : OnBackPressed {
            override fun backPressed(): Boolean {
                return when {
                    mainActivityViewModel.selectedProduct.value != null -> {
                        mainActivityViewModel.unselectProduct()
                        true
                    }
                    mainActivityViewModel.isSearchActive -> {
                        mainActivityViewModel.stopSearch()
                        fragmentProductListBinding.fragmentProductListSearchBar.apply {
                            clearFocus()
                            setText("")
                        }
                        false
                    }
                    else -> {
                        true
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        fragmentProductListBinding.apply {
            productListRecyclerView.apply {
                productListRecyclerViewAdapter.submitList(listOf())
                adapter = productListRecyclerViewAdapter
                post {
                    startPostponedEnterTransition()
                }
                /* viewTreeObserver.addOnPreDrawListener {
                     startPostponedEnterTransition()
                     true
                 }*/
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
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(
                        recyclerView: RecyclerView,
                        newState: Int
                    ) {
                        if (isKeyBoardOpen) {
                            hideKeyBoard()
                        }
                    }
                })
            }
            fragmentProductListSearchBar.apply {
                addTextChangedListener {
                    if (mainActivityViewModel.allProductsWithReviews.value?.isNotEmpty()
                        == true
                    ) {
                        mainActivityViewModel.filterProductList(it.toString())
                    }
                }
                setOnEditorActionListener { _, actionId, _ ->
                    return@setOnEditorActionListener when (actionId) {
                        EditorInfo.IME_ACTION_SEARCH -> {
                            if (mainActivityViewModel.allProductsWithReviews.value?.isNotEmpty()
                                == true
                            ) {
                                mainActivityViewModel.filterProductList(text.toString())
                            }
                            true
                        }
                        else -> false
                    }
                }
                setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        newKeyBoardState(true)
                    }
                }
            }


            swipeToRefreshProductList.setOnRefreshListener {
                mainActivityViewModel.refreshProductList()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentProductListBinding = FragmentProductListBinding
            .inflate(inflater, container, false)

        return fragmentProductListBinding.root
    }

    private fun newKeyBoardState(isKeyBoardOpen: Boolean) {
        this.isKeyBoardOpen = isKeyBoardOpen
    }

    fun hideKeyBoard() {
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
        fragmentProductListBinding.fragmentProductListSearchBar.clearFocus()
        newKeyBoardState(false)
    }

    override fun onStart() {
        super.onStart()
        observeMainActivityViewModelLiveData()
    }

    private fun observeMainActivityViewModelLiveData() {
        mainActivityViewModel.apply {
            allProductsWithReviews.observe(this@ProductListFragment) {
                it?.let {
                    productListRecyclerViewAdapter.submitList(it)
                }
            }
            networkResult.observe(this@ProductListFragment) {
                it?.let {
                    fragmentProductListBinding.swipeToRefreshProductList.isRefreshing = false
                    when (it) {
                        is NetworkResult.Success -> {
                            fragmentProductListBinding.fragmentProductListUserLabel.animate()
                                .alpha(0f)
                        }
                        is NetworkResult.NoInternetConnection -> {
                            if (allProductsWithReviews.value?.isEmpty() == true) {
                                fragmentProductListBinding.fragmentProductListUserLabel.apply {
                                    animate().alpha(1f)
                                    text = getString(R.string.no_internet_connection_label)
                                }
                            }
                        }
                        is NetworkResult.HttpError -> {
                            fragmentProductListBinding.fragmentProductListUserLabel.apply {
                                animate().alpha(1f)
                                text = getString(R.string.server_downtime)
                            }
                        }
                    }
                }
            }

            searchResult.observe(this@ProductListFragment) {
                productListRecyclerViewAdapter.submitList(
                    it ?: mainActivityViewModel.allProductsWithReviews.value
                )

                it?.let {
                    productListRecyclerViewAdapter.submitList(it)
                    if (it.isEmpty()) {
                        fragmentProductListBinding.fragmentProductListUserLabel.text =
                            String.format(
                                getString(
                                    R.string.no_results_found_label,
                                    fragmentProductListBinding.fragmentProductListSearchBar.text.toString()
                                )
                            )
                        fragmentProductListBinding.fragmentProductListUserLabel.animate().alpha(1f)
                    } else {
                        fragmentProductListBinding.fragmentProductListUserLabel.animate().alpha(0f)
                    }
                }
            }
        }
    }
}