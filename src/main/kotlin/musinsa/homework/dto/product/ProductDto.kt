package musinsa.homework.dto.product

import musinsa.homework.domain.Product

data class ProductDto(
    val productId: Long,
    val price: Int,
    val brandId: Long,
    val brandName: String,
    val categoryId: Long,
    val categoryName: String
) {
    companion object {
        fun from(product: Product): ProductDto {
            return ProductDto(
                productId = product.id,
                price = product.price,
                brandId = product.brand.id,
                brandName = product.brand.name,
                categoryId = product.category.id,
                categoryName = product.category.name
            )
        }
    }
}
