package musinsa.homework.service

import musinsa.homework.domain.Category
import musinsa.homework.domain.Product
import musinsa.homework.dto.price.CategoryCheapestProduct
import musinsa.homework.dto.price.CheapestBrandInfo
import musinsa.homework.dto.price.CheapestBrandProduct
import musinsa.homework.dto.price.CheapestBrandResponse
import musinsa.homework.dto.price.CheapestProductResponse
import musinsa.homework.dto.price.LowestAndHighestProductInfo
import musinsa.homework.dto.price.LowestAndHighestProductResponse
import musinsa.homework.exception.DataNotFoundException
import musinsa.homework.exception.ErrorCode
import musinsa.homework.exception.PolicyException
import musinsa.homework.repository.ProductJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PriceService(
    private val productJpaRepository: ProductJpaRepository,
    private val categoryCacheService: CategoryCacheService,
    private val priceCacheService: PriceCacheService
) {
    @Transactional(readOnly = true)
    fun getLowestAndHighestPriceProductsByCategoryName(categoryName: String): LowestAndHighestProductResponse {
        val category = findCategoryByName(categoryName)

        val lowestPriceProductId = priceCacheService.getCategoryLowestPriceProductId(category.id)
        val highestPriceProductId = priceCacheService.getCategoryHighestPriceProductId(category.id)

        val lowestProduct = productJpaRepository.findByIdOrNull(lowestPriceProductId)
        val highestProduct = productJpaRepository.findByIdOrNull(highestPriceProductId)

        if (lowestProduct == null && highestProduct == null) {
            throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "해당 카테고리의 상품이 존재하지 않습니다.")
        }
        if (lowestProduct?.id == highestProduct?.id) {
            throw PolicyException(ErrorCode.POLICY_VIOLATION, "최저가 상품과 최고가 상품이 동일합니다.")
        }

        return LowestAndHighestProductResponse(
            categoryName = category.name,
            lowestProduct = LowestAndHighestProductInfo.from(lowestProduct!!),
            highestProduct = LowestAndHighestProductInfo.from(highestProduct!!)
        )
    }

    @Transactional(readOnly = true)
    fun getCheapestProductsByCategory(): CheapestProductResponse {
        val categories = findAllCategory()

        val cheapestProducts = categories.map { category ->
            val cheapestProductId = priceCacheService.getCategoryLowestPriceProductId(category.id)
            val cheapestProduct = productJpaRepository.findByIdOrNull(cheapestProductId)

            if (cheapestProduct == null) {
                CategoryCheapestProduct(category.name, null, null)
            } else {
                CategoryCheapestProduct.from(cheapestProduct)
            }
        }

        return CheapestProductResponse(
            products = cheapestProducts,
            totalPrice = cheapestProducts.sumOf { it.price ?: 0 }
        )
    }

    @Transactional(readOnly = true)
    fun getCheapestBrands(): CheapestBrandResponse {
        val cheapestBrandId = priceCacheService.getLowestPriceBrandId() ?: throw DataNotFoundException(
            ErrorCode.DATA_NOT_FOUND,
            "최저 가격의 브랜드 정보를 찾을 수 없습니다."
        )
        val cheapestBrandProducts = findAllProductByBrandId(cheapestBrandId)
        val totalPrice = cheapestBrandProducts.sumOf { it.price }
        return CheapestBrandResponse(
            lowestPrice = CheapestBrandInfo(
                brandName = cheapestBrandProducts.first().brand.name,
                categories = cheapestBrandProducts.map { CheapestBrandProduct.from(it) },
                totalPrice = totalPrice
            )
        )
    }

    private fun findCategoryByName(categoryName: String): Category {
        return categoryCacheService.getAllCategories().find { it.name == categoryName }
            ?: throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "카테고리 정보를 찾을 수 없습니다.")
    }

    private fun findAllCategory(): List<Category> {
        val categories = categoryCacheService.getAllCategories()
        if (categories.isEmpty()) {
            throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "카테고리 정보를 찾을 수 없습니다.")
        }
        return categories.sortedBy { it.id }
    }

    private fun findAllProductByBrandId(brandId: Long): List<Product> {
        val products = productJpaRepository.findAllByBrandIdOrderByCategoryIdAsc(brandId)
        if (products.isEmpty()) {
            throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "해당 브랜드의 상품이 존재하지 않습니다.")
        }
        return products
    }
}
