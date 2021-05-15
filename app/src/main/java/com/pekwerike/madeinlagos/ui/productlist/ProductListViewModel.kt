package com.pekwerike.madeinlagos.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private var allProducts: List<Product> = listOf()

    init {
        getAllProducts()
    }

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val productsAndNetworkState = mainRepositoryAPI.fetchAllProducts()
            allProducts = productsAndNetworkState.productList
            withContext(Dispatchers.Main) {
                _productList.value = productsAndNetworkState.productList
            }
        }
    }

    fun filterProductList(text: String) {
        viewModelScope.launch {
            _productList.value = withContext(Dispatchers.IO) {
                productList.value?.filter {
                    it.name.contains(text)
                            || it.description.contains(text)
                            || it.productImageUrl.contains(text)
                }
            }!!
        }
    }
}
