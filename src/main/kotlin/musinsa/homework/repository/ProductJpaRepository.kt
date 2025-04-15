package musinsa.homework.repository

import musinsa.homework.domain.Product
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = ["brand", "category"])
    fun findAllByBrandIdOrderByCategoryIdAsc(brandId: Long): List<Product>

    @EntityGraph(attributePaths = ["brand", "category"])
    fun findAllByCategoryIdOrderByIdAsc(categoryId: Long): List<Product>
}
