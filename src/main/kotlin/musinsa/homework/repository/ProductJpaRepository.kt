package musinsa.homework.repository

import musinsa.homework.domain.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<Product, Long> {
    fun findAllByBrandId(brandId: Long): List<Product>
    fun findAllByCategoryId(categoryId: Long): List<Product>
}
