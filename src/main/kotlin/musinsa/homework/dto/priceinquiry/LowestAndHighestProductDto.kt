package musinsa.homework.dto.priceinquiry

import musinsa.homework.dto.product.ProductDto

data class LowestAndHighestProductDto(
    val categoryId: Long,
    val categoryName: String,
    val lowestProduct: ProductDto,
    val highestProduct: ProductDto
)
