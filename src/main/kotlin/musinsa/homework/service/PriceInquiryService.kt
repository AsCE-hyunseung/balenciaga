package musinsa.homework.service

import musinsa.homework.dto.priceinquiry.LowestAndHighestProductDto
import musinsa.homework.repository.BrandJpaRepository
import musinsa.homework.repository.CategoryJpaRepository
import musinsa.homework.repository.ProductJpaRepository
import org.springframework.stereotype.Service

@Service
class PriceInquiryService(
    private val productJpaRepository: ProductJpaRepository,
    private val brandJpaRepository: BrandJpaRepository,
    private val categoryJpaRepository: CategoryJpaRepository,
) {
    fun getLowestAndHighestProductsByCategoryName(categoryName: String): List<LowestAndHighestProductDto> {
        // TODO: 미리 최고가 상품, 최저가 상품 cache
    }
}