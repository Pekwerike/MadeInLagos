package com.pekwerike.madeinlagos.model

data class Product(
    val id: String = "MIL12",
    val name: String = "product",
    val description: String = "description",
    val currency: String,
    val price: Int,
    val productImageUrl: String =
        "https://assets.adidas.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/ccee9677f7794f5b9db2ad1a00a1872b_9366/adidas_4DFWD_Shoes_Black_FY3963_01_standard.jpg",
    val productReviews: List<ProductReview> = listOf()
) {
    fun getProductPriceWithCurrency() : String = "$currency$price"

}
