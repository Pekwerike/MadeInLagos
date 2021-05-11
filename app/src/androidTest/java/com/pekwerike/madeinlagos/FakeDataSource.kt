package com.pekwerike.madeinlagos

import android.content.Context
import com.pekwerike.madeinlagos.model.Product
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

object FakeDataSource {

    fun getProducts(context: Context): String {
        val products = StringBuilder()
        context.assets.open("Products.json").also { inputStream: InputStream ->
            val inputReader = InputStreamReader(inputStream, "UTF-8")
            inputReader.readLines().forEach {
                products.append(it)
            }
        }
        return products.toString()
    }
}