package musinsa.homework.listener

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ProductEventListener {

    @Async(value = "productEventAsyncExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun changeEvent(event: ProductChangeEvent) {
        // cache change
        println("ProductEventListener: $event")
    }
}
