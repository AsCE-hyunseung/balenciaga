package musinsa.homework.config

import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Component

@Component
@EnableAsync
class AsyncExecutorConfiguration {
    @Bean
    fun productEventAsyncExecutor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 10
        executor.maxPoolSize = 30
        executor.queueCapacity = 60
        executor.threadNamePrefix = "productEventAsyncExecutor-"
        executor.initialize()
        return executor
    }
}
