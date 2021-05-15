package com.pekwerike.madeinlagos.ui.productreview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.repository.MainRepositoryAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductReviewViewModel @Inject constructor(
    private val mainRepositoryAPI: MainRepositoryAPI
) : ViewModel() {

    private val _reviewPostNetworkResult = MutableLiveData<NetworkResult>()
    val reviewPostNetworkResult: LiveData<NetworkResult>
        get() = _reviewPostNetworkResult

    fun postProductReview(productId: String, userRating: Int, userReview: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val networkResult = mainRepositoryAPI.postProductReview(
                productId,
                userRating,
                userReview
            )
            withContext(Dispatchers.Main) {
                _reviewPostNetworkResult.value = networkResult
            }
        }
    }
}