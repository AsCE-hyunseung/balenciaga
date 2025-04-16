package musinsa.homework.repository

import musinsa.homework.domain.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryJpaRepository : JpaRepository<Category, Long>
