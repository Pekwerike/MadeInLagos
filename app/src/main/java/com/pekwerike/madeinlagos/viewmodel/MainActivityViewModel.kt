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

    val allProductsWithReviews: LiveData<List<Product>> =
        mainRepositoryAPI.allProductsWithReviews.asLiveData()

    private val _networkResult = MutableLiveData<NetworkResult>(NetworkResult.NoInternetConnection)
    val networkResult: LiveData<NetworkResult>
        get() = _networkResult

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product>
        get() = _selectedProduct

    init {
        refreshProductList()
    }

    fun refreshProductList() {
        viewModelScope.launch {
            _networkResult.value = withContext(Dispatchers.IO) {
                mainRepositoryAPI.refreshProductList()
            }!!
        }
    }

    fun selectProduct(product: Product) {
        _selectedProduct.value = product
    }
}