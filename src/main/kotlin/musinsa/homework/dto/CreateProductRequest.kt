package musinsa.homework.dto

data class CreateProductRequest(
    val price: Int,
    val brandId: Long,
    val categoryId: Long
)
