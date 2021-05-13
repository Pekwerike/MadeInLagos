package com.pekwerike.madeinlagos.repository.impl

import com.pekwerike.madeinlagos.database.MadeInLagosLocalDatabase
import com.pekwerike.madeinlagos.database.ProductWithReviews
import com.pekwerike.madeinlagos.mappers.productReviewToProductReviewEntityList
import com.pekwerike.madeinlagos.mappers.toProductEntityList
import com.pekwerike.madeinlagos.mappers.toProductReviewList
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.network.ProductReviewAPI
import com.pekwerike.madeinlagos.network.ProductServiceAPI
import com.pekwerike.madeinlagos.repository.MainRepositoryAPI
import com.pekwerike.madeinlagos.utils.ProductDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MadeInLagosProductRepository @Inject constructor(
    madeInLagosLocalDatabase: MadeInLagosLocalDatabase,
    private val networkProductService: ProductServiceAPI,
    private val networkProductReview: ProductReviewAPI,
    private val productDataSource: ProductDataSource,
) : MainRepositoryAPI {
    private val productDao = madeInLagosLocalDatabase.productDAO()
    private val productReviewDao = madeInLagosLocalDatabase.productReviewDAO()
    private val allImages = productDataSource.getProductImagesUrl()

    override val allProductsWithReviews: Flow<List<Product>> =
        madeInLagosLocalDatabase.productDAO().getAllProductWithReviews().map {
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
}