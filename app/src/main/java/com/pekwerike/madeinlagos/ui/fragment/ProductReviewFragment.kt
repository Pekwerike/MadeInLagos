package com.pekwerike.madeinlagos.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pekwerike.madeinlagos.R
import com.pekwerike.madeinlagos.databinding.FragmentProductReviewBinding


class ProductReviewFragment : Fragment() {

    private lateinit var fragmentProductReviewBinding: FragmentProductReviewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductReviewBinding = FragmentProductReviewBinding.inflate(
            inflater,
            container, false
        )
        fragmentProductReviewBinding.fragmentProductReviewToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.post_review_menu_item) {
                // get the rating value
                val rating = fragmentProductReviewBinding.productReviewRatingBar.rating
                // get the text
                val productReviewText =
                    fragmentProductReviewBinding.productReviewEditText.text.toString()
                // post the rating and the text to the server

                true
            } else false
        }
        // Inflate the layout for this fragment
        return fragmentProductReviewBinding.root
    }

}