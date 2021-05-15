package com.pekwerike.madeinlagos.ui.recyclerviewcomponents.productlist

import android.view.View
import android.widget.ImageView
import com.google.android.material.card.MaterialCardView
import com.pekwerike.madeinlagos.model.Product

class ProductItemClickListener(
    private val onClickListener: (
        Product,
        View
    ) -> Unit
) {
    fun onClick(product: Product, clickedView: View) =
        onClickListener(product, clickedView)
}