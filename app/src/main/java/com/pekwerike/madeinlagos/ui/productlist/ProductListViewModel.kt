package com.pekwerike.madeinlagos.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.repository.MainRepositoryAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val mainRepositoryAPI: MainRepositoryAPI
) : ViewModel() {

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>>
        get() = _productList

    private val _productFetchNetworkResult = MutableLiveData<NetworkResult>()
    val productFetchNetworkResult: LiveData<NetworkResult>
        get() = _productFetchNetworkResult
    private var allProducts: List<Product> = listOf()

    init {
        getCachedProducts()
        fetchNewProductsFromNetwork()
    }

    private fun getCachedProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            allProducts = mainRepositoryAPI.getCachedProducts()
            withContext(Dispatchers.Main) {
                if(allProducts.isEmpty()){
                    _productFetchNetworkResult.value = NetworkResult.FetchingDataFromServer
                }
                _productList.value = allProducts
            }
        }
    }

    fun fetchNewProductsFromNetwork() {
        viewModelScope.launch(Dispatchers.IO) {
            val productsAndNetworkState = mainRepositoryAPI.fetchAllProducts()
            allProducts = productsAndNetworkState.productList
            withContext(Dispatchers.Main) {
                _productList.value = productsAndNetworkState.productList
                _productFetchNetworkResult.value = productsAndNetworkState.networkState
            }
        }
    }

    fun filterProductList(text: String) {
        viewModelScope.launch {
            _productList.value = withContext(Dispatchers.IO) {
                allProducts.filter {
                    it.name.contains(text)
                            || it.description.contains(text)
                            || it.productImageUrl.contains(text)
                }
            }!!
        }
    }
}
