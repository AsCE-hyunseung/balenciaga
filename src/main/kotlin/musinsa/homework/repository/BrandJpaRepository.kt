package musinsa.homework.repository

import musinsa.homework.domain.Brand
import org.springframework.data.jpa.repository.JpaRepository

interface BrandJpaRepository : JpaRepository<Brand, Long>
