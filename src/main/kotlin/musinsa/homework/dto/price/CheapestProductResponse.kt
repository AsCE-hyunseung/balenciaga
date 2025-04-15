package musinsa.homework.dto.price

import musinsa.homework.domain.Product

data class CheapestProductResponse(
    val products: List<CategoryCheapestProduct>,
    val totalPrice: Int
)

// category에 상품이 없을 수도 있음
data class CategoryCheapestProduct(
    val categoryName: String,
    val brandName: String?,
    val price: Int?
) {
    companion object {
        fun from(product: Product): CategoryCheapestProduct {
            return CategoryCheapestProduct(
                categoryName = product.category.name,
                brandName = product.brand.name,
                price = product.price
            )
        }
    }
}
