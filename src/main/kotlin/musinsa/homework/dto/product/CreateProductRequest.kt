package musinsa.homework.dto.product

data class CreateProductRequest(
    val price: Int,
    val brandId: Long,
    val categoryId: Long
)
