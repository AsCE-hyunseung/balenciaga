package musinsa.homework.dto.price

import musinsa.homework.dto.product.ProductDto

data class LowestAndHighestProductResponse(
    val categoryId: Long,
    val categoryName: String,
    val lowestProduct: ProductDto,
    val highestProduct: ProductDto
)
