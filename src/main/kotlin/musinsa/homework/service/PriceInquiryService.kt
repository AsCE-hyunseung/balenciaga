package musinsa.homework.service

import musinsa.homework.domain.Category
import musinsa.homework.dto.priceinquiry.LowestAndHighestProductDto
import musinsa.homework.dto.product.ProductDto
import musinsa.homework.exception.DataNotFoundException
import musinsa.homework.exception.ErrorCode
import musinsa.homework.exception.PolicyException
import musinsa.homework.repository.CategoryJpaRepository
import musinsa.homework.repository.ProductJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PriceInquiryService(
    private val productJpaRepository: ProductJpaRepository,
    private val categoryJpaRepository: CategoryJpaRepository
) {

    @Transactional(readOnly = true)
    fun getLowestAndHighestPriceProductsByCategoryName(categoryName: String): LowestAndHighestProductDto {
        val category = findCategoryByName(categoryName)
        val products = productJpaRepository.findAllByCategoryId(category.id)

        val lowestProduct = products.minByOrNull { it.price }
        val highestProduct = products.maxByOrNull { it.price }

        if (lowestProduct == null && highestProduct == null) {
            throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "해당 카테고리의 상품이 존재하지 않습니다.")
        }
        if (lowestProduct?.id == highestProduct?.id) {
            throw PolicyException(ErrorCode.POLICY_VIOLATION, "최저가 상품과 최고가 상품이 동일합니다.")
        }

        return LowestAndHighestProductDto(
            categoryId = category.id,
            categoryName = category.name,
            lowestProduct = ProductDto.from(lowestProduct!!),
            highestProduct = ProductDto.from(highestProduct!!)
        )
    }

    private fun findCategoryByName(categoryName: String): Category {
        return categoryJpaRepository.findByName(categoryName)
            ?: throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "카테고리 정보를 찾을 수 없습니다.")
    }
}
