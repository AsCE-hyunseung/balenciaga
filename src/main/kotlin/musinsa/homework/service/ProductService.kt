package musinsa.homework.service

import musinsa.homework.repository.ProductJpaRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productJpaRepository: ProductJpaRepository,
) {
}