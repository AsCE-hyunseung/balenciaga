package musinsa.homework.service

import musinsa.homework.config.CacheKeys.CATEGORY
import musinsa.homework.domain.Category
import musinsa.homework.repository.CategoryJpaRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryCacheService(
    private val categoryJpaRepository: CategoryJpaRepository
) {
    /**
     * 카테고리 정보를 캐싱합니다.
     */
    @Transactional(readOnly = true)
    @Cacheable(value = [CATEGORY])
    fun getAllCategories(): List<Category> {
        return categoryJpaRepository.findAll()
    }
}
