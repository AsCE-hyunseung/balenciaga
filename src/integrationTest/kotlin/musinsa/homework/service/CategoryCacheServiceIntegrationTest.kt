package musinsa.homework.service

import io.kotest.matchers.shouldNotBe
import musinsa.homework.config.CacheKeys
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cache.CacheManager
import org.springframework.cache.interceptor.SimpleKey
import org.springframework.test.context.TestConstructor

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CategoryCacheServiceIntegrationTest(
    private val cacheManager: CacheManager,
    private val categoryCacheService: CategoryCacheService
) {

    @BeforeEach
    fun delete() {
        cacheManager.getCache(CacheKeys.CATEGORY)?.clear()
    }

    @Test
    fun `getAllCategories 캐시 테스트`() {
        // given
        categoryCacheService.getAllCategories()

        // when
        val result = cacheManager.getCache(CacheKeys.CATEGORY)?.get(SimpleKey())?.get() as List<*>
        // then
        result.size shouldNotBe 0
    }
}
