package com.pekwerike.madeinlagos.ui.recyclerviewcomponents

import com.pekwerike.madeinlagos.model.Product

class ProductItemClickListener(private val onClickListener: (Product) -> Unit) {
    fun onClick(product: Product) = onClickListener(product)
}