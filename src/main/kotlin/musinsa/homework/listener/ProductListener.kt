package musinsa.homework.listener

import jakarta.persistence.PostPersist
import jakarta.persistence.PostUpdate
import musinsa.homework.domain.Product
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class ProductListener(
    private val publisher: ApplicationEventPublisher
) {
    @PostUpdate
    fun postUpdate(product: Product) {
        val event = ProductChangeEvent.from(product)
        publisher.publishEvent(event)
    }

    @PostPersist
    fun postPersist(product: Product) {
        val event = ProductChangeEvent.from(product)
        publisher.publishEvent(event)
    }
}

data class ProductChangeEvent(
    val productId: Long,
    val categoryId: Long,
    val brandId: Long,
    val price: Int
) {
    companion object {
        fun from(product: Product): ProductChangeEvent {
            return ProductChangeEvent(
                productId = product.id,
                categoryId = product.category.id,
                brandId = product.brand.id,
                price = product.price
            )
        }
    }
}
