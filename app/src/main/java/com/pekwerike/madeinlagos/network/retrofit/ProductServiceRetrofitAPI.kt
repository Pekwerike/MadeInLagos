package com.pekwerike.madeinlagos.network.retrofit

import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.model.ProductReview
import com.pekwerike.madeinlagos.network.ProductServiceAPI
import retrofit2.Response
import retrofit2.http.*


interface ProductServiceRetrofitAPI {

    @GET(ProductServiceAPI.BASE_PRODUCT_URL)
    suspend fun getAllProduct(): Response<List<Product>>

    @POST
    suspend fun createProduct(@Url postUrl: String, @Body product: Product): Response<Product>

    @GET
    suspend fun getProductById(@Url productUrl: String): Response<Product>

    @PUT
    suspend fun updateProduct(@Url productUrl: String, @Body product: Product): Response<Product>

    @DELETE
    suspend fun deleteProduct(@Url productUrl: String)

    @POST
    suspend fun postProductReview(
        @Url productReviewUrl: String,
        @Body productReview: ProductReview
    ): Response<ProductReview>

    @GET
    suspend fun getReviewsForProduct(@Url productReviewUrl: String): Response<List<ProductReview>>

}