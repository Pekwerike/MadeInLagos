package com.pekwerike.madeinlagos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.repository.MadeInLagosProductRepository
import com.pekwerike.madeinlagos.repository.MainRepositoryAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mainRepositoryAPI: MainRepositoryAPI
) : ViewModel() {
    val allProductsWithReviews: LiveData<List<Product>> =
        mainRepositoryAPI.allProductsWithReviews.asLiveData()


    fun refreshProductList() {
        viewModelScope.launch(Dispatchers.IO) {
            val networkResult = mainRepositoryAPI.refreshProductList()
        }
    }
}