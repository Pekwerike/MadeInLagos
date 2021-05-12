package com.pekwerike.madeinlagos.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.pekwerike.madeinlagos.R
import com.pekwerike.madeinlagos.databinding.FragmentProductDetailBinding
import com.pekwerike.madeinlagos.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private lateinit var fragmentProductDetailBinding: FragmentProductDetailBinding
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductDetailBinding = FragmentProductDetailBinding.inflate(
            inflater,
            container,
            false
        ).apply {

        }
        // Inflate the layout for this fragment
        return fragmentProductDetailBinding.root
    }

    private fun observeMainActivityViewModelLiveData() {
        mainActivityViewModel.selectedProduct.observe(this) {
            it?.let {
                fragmentProductDetailBinding.apply {

                }
            }
        }
    }
}