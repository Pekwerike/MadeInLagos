package com.pekwerike.madeinlagos.ui.productdetail

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
class ProductDetailActivityViewModel @Inject constructor(private val mainRepositoryAPI: MainRepositoryAPI) :
    ViewModel() {

    private val _currentProductInDisplay = MutableLiveData<Product>()
    val currentProductInDisplay: LiveData<Product>
        get() = _currentProductInDisplay

    fun getProductById(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val product = mainRepositoryAPI.getProductById(productId)
            withContext(Dispatchers.Main) {
                _currentProductInDisplay.value = product
            }
        }
    }
}