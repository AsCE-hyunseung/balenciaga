package musinsa.homework.service

import musinsa.homework.config.CacheKeys.CATEGORY_HIGHEST_PRICE_PRODUCT_ID
import musinsa.homework.config.CacheKeys.CATEGORY_LOWEST_PRICE_PRODUCT_ID
import musinsa.homework.config.CacheKeys.LOWEST_PRICE_BRAND_ID
import musinsa.homework.domain.Brand
import musinsa.homework.exception.DataNotFoundException
import musinsa.homework.exception.ErrorCode
import musinsa.homework.repository.BrandJpaRepository
import musinsa.homework.repository.ProductJpaRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PriceCacheService(
    private val productJpaRepository: ProductJpaRepository,
    private val brandJpaRepository: BrandJpaRepository
) {
    @CacheEvict(value = [LOWEST_PRICE_BRAND_ID])
    fun cacheEvictLowestPriceBrandId() {
    }

    @CacheEvict(value = [CATEGORY_HIGHEST_PRICE_PRODUCT_ID], key = "#categoryId")
    fun cacheEvictCategoryHighestPriceProductId(categoryId: Long) {
    }

    @CacheEvict(value = [CATEGORY_LOWEST_PRICE_PRODUCT_ID], key = "#categoryId")
    fun cacheEvictCategoryLowestPriceProductId(categoryId: Long) {
    }

    /**
     * 브랜드별 상품 가격 총 합이 가장 낮은 브랜드 ID를 캐싱합니다.
     */
    @Cacheable(value = [LOWEST_PRICE_BRAND_ID])
    @Transactional(readOnly = true)
    fun getLowestPriceBrandId(): Long? {
        val brands = findAllBrand()
        val brandPriceMap = brands.associateWith { brand ->
            val products = productJpaRepository.findAllByBrandIdOrderByCategoryIdAsc(brand.id)

            // 해당 브랜드에 상품이 없는 경우 null 처리
            if (products.isEmpty()) {
                null
            } else {
                val totalPrice = products.sumOf { it.price }
                totalPrice
            }
        }
        val (cheapestBrand, _) = brandPriceMap
            .filterValues { it != null }
            .minByOrNull { it.value!! } ?: return null
        return cheapestBrand.id
    }

    /**
     * 카테고리별 상품 가격 총 합이 가장 높은 상품 ID를 캐싱합니다.
     */
    @Cacheable(value = [CATEGORY_HIGHEST_PRICE_PRODUCT_ID], key = "#categoryId")
    @Transactional(readOnly = true)
    fun getCategoryHighestPriceProductId(categoryId: Long): Long? {
        val products = productJpaRepository.findAllByCategoryIdOrderByIdAsc(categoryId)
        val lowestProduct = products.maxByOrNull { it.price } ?: return null
        return lowestProduct.id
    }

    /**
     * 카테고리별 상품 가격 총 합이 가장 낮은 상품 ID를 캐싱합니다.
     */
    @Cacheable(value = [CATEGORY_LOWEST_PRICE_PRODUCT_ID], key = "#categoryId")
    @Transactional(readOnly = true)
    fun getCategoryLowestPriceProductId(categoryId: Long): Long? {
        val products = productJpaRepository.findAllByCategoryIdOrderByIdAsc(categoryId)
        val lowestProduct = products.minByOrNull { it.price } ?: return null
        return lowestProduct.id
    }

    private fun findAllBrand(): List<Brand> {
        val brands = brandJpaRepository.findAll()
        if (brands.isEmpty()) {
            throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "브랜드 정보를 찾을 수 없습니다.")
        }
        return brands.sortedBy { it.id }
    }
}
