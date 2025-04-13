package musinsa.homework.service

import musinsa.homework.repository.BrandJpaRepository
import org.springframework.stereotype.Service

@Service
class BrandService(
    private val brandJpaRepository: BrandJpaRepository,
) {
}