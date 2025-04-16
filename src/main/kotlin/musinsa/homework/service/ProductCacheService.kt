package musinsa.homework.service

import jakarta.annotation.PostConstruct
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class ProductCacheService(
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    @PostConstruct
    fun initialize() {
        // Initialize the cache or any other setup needed
    }
}
