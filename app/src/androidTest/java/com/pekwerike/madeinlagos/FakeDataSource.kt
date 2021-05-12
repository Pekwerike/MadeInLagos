package com.pekwerike.madeinlagos

import android.content.Context
import com.pekwerike.madeinlagos.model.Product
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

object FakeDataSource {

    @ExperimentalStdlibApi
    fun getProducts(context: Context, moshi: Moshi): List<Product> {
        val products = StringBuilder()
        context.assets.open("Products.json").also { inputStream: InputStream ->
            val inputReader = InputStreamReader(inputStream, "UTF-8")
            inputReader.readLines().forEach {
                products.append(it)
            }
        }
        return moshi.adapter<List<Product>>().fromJson(products.toString()) ?: listOf()
    }
}