package musinsa.homework.repository

import musinsa.homework.domain.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<Product, Long>
