package musinsa.homework.dto.price

import musinsa.homework.domain.Product

data class LowestAndHighestProductResponse(
    val categoryName: String,
    val lowestProduct: LowestAndHighestProductInfo,
    val highestProduct: LowestAndHighestProductInfo
)

data class LowestAndHighestProductInfo(
    val brandName: String,
    val price: Int
) {
    companion object {
        fun from(product: Product): LowestAndHighestProductInfo {
            return LowestAndHighestProductInfo(
                brandName = product.brand.name,
                price = product.price
            )
        }
    }
}
