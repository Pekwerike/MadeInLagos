package com.pekwerike.madeinlagos.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.pekwerike.madeinlagos.R
import com.pekwerike.madeinlagos.databinding.FragmentProductReviewBinding
import com.pekwerike.madeinlagos.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductReviewFragment : Fragment() {

    private lateinit var fragmentProductReviewBinding: FragmentProductReviewBinding
    private val sharedViewModel: MainActivityViewModel by viewModels()
    private val navArgs : ProductReviewFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = MaterialContainerTransform()
        fragmentProductReviewBinding = FragmentProductReviewBinding.inflate(
            inflater,
            container, false
        )
        fragmentProductReviewBinding.fragmentProductReviewToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.post_review_menu_item) {
                // get the rating value
                val userRating = fragmentProductReviewBinding.productReviewRatingBar.rating
                // get the text
                val userReviewText =
                    fragmentProductReviewBinding.productReviewEditText.text.toString()
                // post the rating and the text to the server
                sharedViewModel.postProductReview(navArgs.productId, userRating, userReviewText)
                true
            } else false
        }
        // Inflate the layout for this fragment
        return fragmentProductReviewBinding.root
    }

}