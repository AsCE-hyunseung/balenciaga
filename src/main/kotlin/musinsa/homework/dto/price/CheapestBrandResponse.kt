package musinsa.homework.dto.price

import musinsa.homework.domain.Product

data class CheapestBrandResponse(
    val lowestPrice: CheapestBrandInfo
)

data class CheapestBrandInfo(
    val brandName: String,
    val categories: List<CheapestBrandProduct>,
    val totalPrice: Int
)

data class CheapestBrandProduct(
    val categoryName: String,
    val price: Int
) {
    companion object {
        fun from(product: Product): CheapestBrandProduct {
            return CheapestBrandProduct(
                categoryName = product.category.name,
                price = product.price
            )
        }
    }
}
