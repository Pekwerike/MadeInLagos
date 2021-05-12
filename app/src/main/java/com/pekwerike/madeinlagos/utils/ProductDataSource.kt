package com.pekwerike.madeinlagos.utils

import android.content.Context
import com.pekwerike.madeinlagos.model.Product
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import javax.inject.Inject

/*
ProductDataSource contains functions that parses the product and product reviews json files in
the assets folder into kotlin objects
*/
class ProductDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi
) {
    @ExperimentalStdlibApi
    fun getProducts(): List<Product> {
        val products = StringBuilder()
        context.assets.open("Products.json").also { inputStream: InputStream ->
            val inputReader = InputStreamReader(inputStream, "UTF-8")
            inputReader.readLines().forEach {
                products.append(it)
            }
        }
        return moshi.adapter<List<Product>>().fromJson(products.toString()) ?: listOf()
    }

    fun getProductImagesUrl(): List<String> {
        return context.assets.open("ProductImages").let { inputStream: InputStream ->
            val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
            inputStreamReader.readLines()
        }
    }

}