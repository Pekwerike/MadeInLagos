package com.pekwerike.madeinlagos.viewmodel

import androidx.lifecycle.asLiveData
import com.pekwerike.madeinlagos.repository.MadeInLagosProductRepository
import com.pekwerike.madeinlagos.repository.MainRepositoryAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
   private val mainRepositoryAPI: MainRepositoryAPI
) {
    val allProductsWithReviews = mainRepositoryAPI.allProductsWithReviews.asLiveData()

}