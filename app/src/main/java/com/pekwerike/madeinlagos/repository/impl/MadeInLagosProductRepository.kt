package com.pekwerike.madeinlagos.repository.impl


import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.pekwerike.madeinlagos.database.ProductDAO
import com.pekwerike.madeinlagos.database.ProductReviewDAO
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


class MadeInLagosProductRepository @Inject constructor(
    private val productDao: ProductDAO,
    private val productReviewDao: ProductReviewDAO,
    private val networkProductService: ProductServiceAPI,
    private val networkProductReview: ProductReviewAPI,
    productDataSource: ProductDataSource,
) : MainRepositoryAPI {

    private val allImages = productDataSource.getProductImagesUrl()

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
        return when (val networkResult = networkProductService.getAllProduct()) {
            is NetworkResult.Success.AllProducts -> {
                // insert the fetched products into the database and delete the previous list
                // of products
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

                // retrieve the list of products from the database and return it
                val productList =
                    productDao.getAllProductsWithReviews().productWithReviewsToProductList()
                ProductsAndNetworkState(
                    productList,
                    networkResult
                )
            }
            is NetworkResult.NoInternetConnection -> {
                // return the cached product list
                ProductsAndNetworkState(
                    productDao.getAllProductsWithReviews().productWithReviewsToProductList(),
                    networkResult
                )

            }
            is NetworkResult.HttpError -> {
                // return the cached product list
                ProductsAndNetworkState(
                    productDao.getAllProductsWithReviews().productWithReviewsToProductList(),
                    networkResult
                )
            }
            else -> {
                // return the cached product list
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

    override suspend fun getCachedProductWithReviewsById(productId: String): Product {
        return productDao.getProductWithReviewsByProductId(productId).productWithReviewsToProduct()
    }
}

