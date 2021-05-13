package com.pekwerike.madeinlagos.ui.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("setProductCurrency", "setProductPrice", requireAll = true)
fun TextView.setProductCurrencyAndPrice(currency: String, price: Int) {
    text = "$currency$price"
}