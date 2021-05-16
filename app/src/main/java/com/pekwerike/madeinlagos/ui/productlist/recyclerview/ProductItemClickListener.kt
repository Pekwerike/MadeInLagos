package com.pekwerike.madeinlagos.ui.productlist.recyclerview

import android.view.View
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