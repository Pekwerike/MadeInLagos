package com.pekwerike.madeinlagos.repository

import com.pekwerike.madeinlagos.database.MadeInLagosLocalDatabase
import com.pekwerike.madeinlagos.database.ProductDAO
import com.pekwerike.madeinlagos.database.ProductReviewDAO
import com.pekwerike.madeinlagos.database.ProductWithReviews
import com.pekwerike.madeinlagos.mappers.productReviewToProductReviewEntityList
import com.pekwerike.madeinlagos.mappers.toProductEntityList
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.network.ProductReviewAPI
import com.pekwerike.madeinlagos.network.ProductServiceAPI
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class MadeInLagosProductRepository @Inject constructor(
    madeInLagosLocalDatabase: MadeInLagosLocalDatabase,
    private val networkProductService: ProductServiceAPI,
    private val networkProductReview: ProductReviewAPI
) : MainRepositoryAPI {
    private val productDao = madeInLagosLocalDatabase.productDAO()
    private val productReviewDao = madeInLagosLocalDatabase.productReviewDAO()

    override val allProductsWithReviews: Flow<List<ProductWithReviews>> =
        madeInLagosLocalDatabase.productDAO().getAllProductWithReviews()

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