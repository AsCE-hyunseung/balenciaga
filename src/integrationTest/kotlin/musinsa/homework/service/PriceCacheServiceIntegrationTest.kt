package musinsa.homework.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import musinsa.homework.config.CacheKeys
import musinsa.homework.exception.DataNotFoundException
import musinsa.homework.repository.BrandJpaRepository
import musinsa.homework.repository.CategoryJpaRepository
import musinsa.homework.repository.ProductJpaRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cache.CacheManager
import org.springframework.cache.interceptor.SimpleKey
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
class PriceCacheServiceIntegrationTest(
    private val productJpaRepository: ProductJpaRepository,
    private val brandJpaRepository: BrandJpaRepository,
    private val categoryJpaRepository: CategoryJpaRepository,
    private val priceCacheService: PriceCacheService,
    private val cacheManager: CacheManager
) {
    @BeforeEach
    fun deleteInBatch() {
        cacheManager.getCache(CacheKeys.LOWEST_PRICE_BRAND_ID)?.clear()
        cacheManager.getCache(CacheKeys.CATEGORY_HIGHEST_PRICE_PRODUCT_ID)?.clear()
    }

    @Test
    fun `LOWEST_PRICE_BRAND_ID cache 테스트`() {
        // given
        priceCacheService.getLowestPriceBrandId()
        // when
        val result = cacheManager.getCache(CacheKeys.LOWEST_PRICE_BRAND_ID)?.get(SimpleKey())?.get()
        // then
        result shouldNotBe null
    }

    @Test
    fun `LOWEST_PRICE_BRAND_ID brand가 없으면 에러를 낸다`() {
        // given
        brandJpaRepository.deleteAllInBatch()
        // when, then
        shouldThrow<DataNotFoundException> {
            priceCacheService.getLowestPriceBrandId()
        }
    }

    @Test
    fun `LOWEST_PRICE_BRAND_ID 모든 brand에 product가 없으면 null 캐싱을 한다`() {
        // given
        productJpaRepository.deleteAllInBatch()
        priceCacheService.getLowestPriceBrandId()
        // when
        val result = cacheManager.getCache(CacheKeys.LOWEST_PRICE_BRAND_ID)?.get(SimpleKey())
        // then
        result shouldNotBe null
        result!!.get() shouldBe null
    }

    @Test
    fun `LOWEST_PRICE_BRAND_ID cache evict 테스트`() {
        // given
        priceCacheService.getLowestPriceBrandId()
        priceCacheService.cacheEvictLowestPriceBrandId()

        // when
        val result = cacheManager.getCache(CacheKeys.LOWEST_PRICE_BRAND_ID)?.get(SimpleKey())?.get()

        // then
        result shouldBe null
    }

    @Test
    fun `CATEGORY_HIGHEST_PRICE_PRODUCT_ID cache 테스트`() {
        // given
        val categories = categoryJpaRepository.findAll()

        categories.forEach {
            priceCacheService.getCategoryHighestPriceProductId(it.id)
        }

        // when
        val result = categories.mapNotNull {
            cacheManager.getCache(CacheKeys.CATEGORY_HIGHEST_PRICE_PRODUCT_ID)?.get(it.id)?.get()
        }

        // then
        result.size shouldBe categories.size
    }

    @Test
    fun `CATEGORY_HIGHEST_PRICE_PRODUCT_ID 특정 캐시 하나만 cache evict 테스트`() {
        // given
        val categories = categoryJpaRepository.findAll()

        categories.forEach {
            priceCacheService.getCategoryHighestPriceProductId(it.id)
        }

        priceCacheService.cacheEvictCategoryHighestPriceProductId(categories.first().id)

        // when
        val result = categories.mapNotNull {
            cacheManager.getCache(CacheKeys.CATEGORY_HIGHEST_PRICE_PRODUCT_ID)?.get(it.id)?.get()
        }

        // then
        result.size shouldBe (categories.size - 1)
    }
}
