package musinsa.homework.dto.product

import jakarta.validation.constraints.NotBlank

data class CreateProductRequest(
    @field:NotBlank(message = "가격은 필수입니다.")
    val price: Int,
    @field:NotBlank(message = "브랜드 ID는 필수입니다.")
    val brandId: Long,
    @field:NotBlank(message = "카테고리 ID는 필수입니다.")
    val categoryId: Long
)
