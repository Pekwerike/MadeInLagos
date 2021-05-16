package com.pekwerike.madeinlagos.repository.impl

import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.pekwerike.madeinlagos.database.MadeInLagosLocalDatabase
import com.pekwerike.madeinlagos.database.ProductWithReviews
import com.pekwerike.madeinlagos.mappers.*
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.model.ProductReview
import com.pekwerike.madeinlagos.model.ProductsAndNetworkState
import com.pekwerike.madeinlagos.network.ProductReviewAPI
import com.pekwerike.madeinlagos.network.ProductServiceAPI
import com.pekwerike.madeinlagos.repository.MainRepositoryAPI
import com.pekwerike.madeinlagos.utils.ProductDataSource
import javax.inject.Inject
import java.net.UnknownHostException
import javax.inject.Singleton
import kotlin.math.roundToInt


class MadeInLagosProductRepository @Inject constructor(
    madeInLagosLocalDatabase: MadeInLagosLocalDatabase,
    private val networkProductService: ProductServiceAPI,
    private val networkProductReview: ProductReviewAPI,
    productDataSource: ProductDataSource,
) : MainRepositoryAPI {
    private val productDao = madeInLagosLocalDatabase.productDAO()
    private val productReviewDao = madeInLagosLocalDatabase.productReviewDAO()
    private val allImages = productDataSource.getProductImagesUrl()

    override val allProductsWithReviewsAsLiveData: LiveData<List<Product>> =
        madeInLagosLocalDatabase.productDAO().getAllProductWithReviewsAsLiveData().map {
            it.map { productWithReviews: ProductWithReviews ->
                Product(
                    id = productWithReviews.product.productId,
                    name = productWithReviews.product.name,
                    description = productWithReviews.product.description,
                    currency = productWithReviews.product.currency,
                    price = productWithReviews.product.price,
                    productImageUrl = allImages.random(),
                    productReviews = productWithReviews.reviews.toProductReviewList()
                )
            }
        }

    override suspend fun getCachedProducts(): List<Product> {
        return productDao.getAllProductsWithReviews().productWithReviewsToProductList()
    }

    override suspend fun refreshProductList(): NetworkResult {
        // fetch products from network
        val networkResult = networkProductService.getAllProduct()
        if (networkResult is NetworkResult.Success.AllProducts) {
            // insert the retrieved products into the local cache
            productDao.refreshProductList(networkResult.products.toProductEntityList())
            // all changes will be notified via the allProductsWithReviews flow
        }
        return networkResult
    }

    override suspend fun getProductReviewsByProductId(productId: String): NetworkResult {
        val networkResult = networkProductReview.getProductReviews(productId)
        if (networkResult is NetworkResult.Success.ProductReviews) {
            // insert the retrieved product reviews into the local cache
            productReviewDao.refreshProductReviewsByProductId(
                productId,
                networkResult.productReviews.productReviewToProductReviewEntityList()
            )
        }
        return networkResult
    }

    override suspend fun postProductReview(
        productId: String,
        userRating: Int,
        userReviewText: String
    ): Pair<ProductReview?, NetworkResult> {

        return when (val networkResult = networkProductReview.postProductReview(
            ProductReview(
                productId = productId,
                rating = userRating,
                text = userReviewText
            )
        )) {
            is NetworkResult.Success.SingleProductReview -> {
                Pair(networkResult.productReview, networkResult)
            }
            else -> {
                Pair(null, networkResult)
            }
        }
    }


    override suspend fun getProductById(productId: String): Product {
        return productDao.getProductWithReviewsByProductId(productId)
            .productWithReviewsToProduct()
    }

    override suspend fun fetchAllProducts(): ProductsAndNetworkState {
        // get products from network first
        // insert the fetched products into the database
        // retrieve the list of products from the database
        // return the list of products and network state to the user
        return when (val networkResult = networkProductService.getAllProduct()) {
            is NetworkResult.Success.AllProducts -> {
                productDao.refreshProductList(networkResult.products.map {
                    Product(
                        id = it.id,
                        name = it.name,
                        description = it.description,
                        currency = it.currency,
                        price = it.price,
                        productImageUrl = allImages.random(),
                        productReviews = it.productReviews
                    )
                }.toProductEntityList())
                val productList =
                    productDao.getAllProductsWithReviews().productWithReviewsToProductList()
                ProductsAndNetworkState(
                    productList,
                    networkResult
                )
            }
            is NetworkResult.NoInternetConnection -> {
                // get the cached product list
                ProductsAndNetworkState(
                    productDao.getAllProductsWithReviews().productWithReviewsToProductList(),
                    networkResult
                )

            }
            is NetworkResult.HttpError -> {
                ProductsAndNetworkState(
                    productDao.getAllProductsWithReviews().productWithReviewsToProductList(),
                    networkResult
                )
            }
            else -> {
                ProductsAndNetworkState(
                    productDao.getAllProductsWithReviews().productWithReviewsToProductList(),
                    networkResult
                )
            }
        }
    }

    override suspend fun getProductWithReviewsById(productId: String): Pair<Product?, NetworkResult> {
        // fetch product reviews from the server
        return when (val networkResult = networkProductReview.getProductReviews(productId)) {
            is NetworkResult.Success.ProductReviews -> {
                // insert product reviews into the database
                productReviewDao.refreshProductReviewsByProductId(
                    productId,
                    networkResult.productReviews.productReviewToProductReviewEntityList()
                )
                // retrieve product along with it's associated reviews from the database
                val product: Product = productDao
                    .getProductWithReviewsByProductId(productId)
                    .productWithReviewsToProduct()
                Pair(product, networkResult)
            }
            is NetworkResult.HttpError -> {
                // get product along with it's reviews from database
                Pair(
                    productDao.getProductWithReviewsByProductId(productId)
                        .productWithReviewsToProduct(), networkResult
                )
            }
            is NetworkResult.NoInternetConnection -> {
                Pair(
                    productDao.getProductWithReviewsByProductId(productId)
                        .productWithReviewsToProduct(), networkResult
                )
            }
            else -> Pair(
                productDao.getProductWithReviewsByProductId(productId)
                    .productWithReviewsToProduct(), networkResult
            )
        }
    }
}

