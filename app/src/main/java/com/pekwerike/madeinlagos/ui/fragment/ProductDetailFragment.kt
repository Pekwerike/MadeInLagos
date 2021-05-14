package com.pekwerike.madeinlagos.ui.fragment

import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.transition.MaterialContainerTransform
import com.pekwerike.madeinlagos.R
import com.pekwerike.madeinlagos.databinding.FragmentProductDetailBinding
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private val args: ProductDetailFragmentArgs by navArgs()
    private lateinit var fragmentProductDetailBinding: FragmentProductDetailBinding
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 420
        }
        observeMainActivityViewModelLiveData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        postponeEnterTransition()
        fragmentProductDetailBinding = DataBindingUtil.inflate<FragmentProductDetailBinding>(
            inflater,
            R.layout.fragment_product_detail,
            container,
            false
        ).apply {
            ViewCompat.setTransitionName(
                fragmentProductDetailContainer,
                args.productId
            )

            fragmentProductDetailsToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
        // Inflate the layout for this fragment
        return fragmentProductDetailBinding.root
    }

    private fun observeMainActivityViewModelLiveData() {
        mainActivityViewModel.selectedProduct.observe(this) {
            it?.let { selectedProduct: Product ->
                fragmentProductDetailBinding.apply {
                    val placeHolder = ColorDrawable(
                            ContextCompat.getColor(
                                requireContext(),
                                android.R.color.darker_gray
                            )
                        ).apply {

                        }
                    Glide.with(fragmentProductDetailProductImageView)
                        .load(selectedProduct.productImageUrl)
                        .placeholder(placeHolder)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                startPostponedEnterTransition()
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                startPostponedEnterTransition()
                                return false
                            }
                        })
                        .into(fragmentProductDetailProductImageView)

                    product = selectedProduct
                }
            }
        }
    }
}