package musinsa.homework.listener

import musinsa.homework.service.PriceCacheService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ProductEventListener(
    private val priceCacheService: PriceCacheService
) {
    @Async(value = "productEventAsyncExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun cacheEvict(event: ProductChangeEvent) {
        priceCacheService.cacheEvictLowestPriceBrandId()
        priceCacheService.cacheEvictCategoryHighestPriceProductId(event.categoryId)
        priceCacheService.cacheEvictCategoryLowestPriceProductId(event.categoryId)
    }
}
