package musinsa.homework.dto.brand

import musinsa.homework.domain.Brand

data class BrandDto(
    val brandId: Long,
    val brandName: String
) {
    companion object {
        fun from(brand: Brand): BrandDto {
            return BrandDto(
                brandId = brand.id,
                brandName = brand.name
            )
        }
    }
}
