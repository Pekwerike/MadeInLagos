package com.pekwerike.madeinlagos.viewmodel

import com.pekwerike.madeinlagos.repository.MadeInLagosProductRepository
import com.pekwerike.madeinlagos.repository.MainRepositoryAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    mainRepositoryAPI: MainRepositoryAPI
) {
}