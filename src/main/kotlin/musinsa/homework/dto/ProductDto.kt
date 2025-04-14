package musinsa.homework.dto

import musinsa.homework.domain.Product

data class ProductDto(
    val productId: Long,
    val price: Int,
    val brandId: Long,
    val categoryId: Long
) {
    companion object {
        fun from(product: Product): ProductDto {
            return ProductDto(
                productId = product.id,
                price = product.price,
                brandId = product.brandId,
                categoryId = product.categoryId
            )
        }
    }
}
