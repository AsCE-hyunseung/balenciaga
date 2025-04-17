package musinsa.homework.dto.brand

import jakarta.validation.constraints.NotBlank

data class CreateBrandRequest(
    @field:NotBlank(message = "브랜드 이름은 필수입니다.")
    val brandName: String
)
