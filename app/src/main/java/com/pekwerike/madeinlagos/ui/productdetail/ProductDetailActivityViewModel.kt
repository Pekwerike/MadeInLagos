package com.pekwerike.madeinlagos.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.repository.MainRepositoryAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductDetailActivityViewModel @Inject constructor(private val mainRepositoryAPI: MainRepositoryAPI) :
    ViewModel() {

    private val _currentProductInDisplay = MutableLiveData<Product>()
    val currentProductInDisplay: LiveData<Product>
        get() = _currentProductInDisplay


    private val _isConnectedToInternet = MutableLiveData<Boolean>(true)
    val isConnectedToInternet: LiveData<Boolean>
        get() = _isConnectedToInternet

    fun getProductWithReviewsFromCache(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val products = mainRepositoryAPI.getCachedProductWithReviewsById(productId)
            withContext(Dispatchers.Main) {
                _currentProductInDisplay.value = products
            }
        }
    }

    fun getProductWithFreshReviewsById(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                // wait for 600mls before signaling to the activity that the network is down
                delay(600)
                withContext(Dispatchers.Main) {
                    _isConnectedToInternet.value = false
                }
            }
            val product: Pair<Product?, NetworkResult> =
                mainRepositoryAPI.getProductWithReviewsById(productId)
            withContext(Dispatchers.Main) {
                if (product.first != null) {
                    _currentProductInDisplay.value = product.first!!
                    _isConnectedToInternet.value = product.second is NetworkResult.Success
                }
            }
        }
    }
}