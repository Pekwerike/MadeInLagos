package com.pekwerike.madeinlagos.viewmodel

import androidx.lifecycle.*
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.repository.MainRepositoryAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mainRepositoryAPI: MainRepositoryAPI
) : ViewModel() {

    var isSearchActive: Boolean = false

    private val _searchResult = MutableLiveData<List<Product>?>(null)
    val searchResult: LiveData<List<Product>?>
        get() = _searchResult

    val allProductsWithReviews: LiveData<List<Product>> =
        mainRepositoryAPI.allProductsWithReviewsAsLiveData

    private val _networkResult = MutableLiveData<NetworkResult>(NetworkResult.NoInternetConnection)
    val networkResult: LiveData<NetworkResult>
        get() = _networkResult

    private val _selectedProduct = MutableLiveData<Product?>()
    val selectedProduct: LiveData<Product?>
        get() = _selectedProduct

    init {
        refreshProductList()
    }

    fun filterProductList(text: String) {
        viewModelScope.launch {
            isSearchActive = text.isNotEmpty()
            _searchResult.value = withContext(Dispatchers.Default) {
                allProductsWithReviews.value?.filter {
                    it.name.contains(text)
                            || it.description.contains(text)
                            || it.productImageUrl.contains(text)
                }
            }!!
        }
    }

    fun stopSearch() {
        isSearchActive = false
        _searchResult.value = null
    }

    fun refreshProductList() {
        viewModelScope.launch(Dispatchers.IO) {
            val networkResult = mainRepositoryAPI.refreshProductList()
            withContext(Dispatchers.Main) {
                _networkResult.value = networkResult
            }
        }
    }

    fun unselectProduct() {
        _selectedProduct.value = null
    }

    fun selectProduct(product: Product) {
        _selectedProduct.value = product
    }

    fun getProductReview() {
        viewModelScope.launch {
            _selectedProduct.value?.let {
                mainRepositoryAPI.getProductReviewsByProductId(it.id)
            }
        }
    }

    fun postProductReview(productId: String, userRating: Float, userReviewText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepositoryAPI.postProductReview(
                productId = productId,
                userRating = userRating.toInt(),
                userReviewText = userReviewText
            )
        }
    }
}