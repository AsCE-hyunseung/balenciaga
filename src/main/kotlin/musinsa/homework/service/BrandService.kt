package musinsa.homework.service

import musinsa.homework.domain.Brand
import musinsa.homework.exception.DataNotFoundException
import musinsa.homework.exception.ErrorCode.DATA_NOT_FOUND
import musinsa.homework.exception.ErrorCode.POLICY_VIOLATION
import musinsa.homework.exception.PolicyException
import musinsa.homework.repository.BrandJpaRepository
import musinsa.homework.repository.ProductJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BrandService(
    private val productJpaRepository: ProductJpaRepository,
    private val brandJpaRepository: BrandJpaRepository
) {
    @Transactional
    fun createBrand(brandName: String): Brand {
        val brand = Brand(name = brandName)
        return brandJpaRepository.save(brand)
    }

    @Transactional
    fun updateBrand(brandId: Long, brandName: String): Brand {
        val brand = findBrandById(brandId)
        brand.updateName(brandName)
        return brand
    }

    @Transactional
    fun deleteBrand(brandId: Long): Boolean {
        val brand = findBrandById(brandId)
        validateNoAssociatedProducts(brandId)
        brandJpaRepository.delete(brand)
        return true
    }

    private fun findBrandById(brandId: Long): Brand {
        return brandJpaRepository.findByIdOrNull(brandId)
            ?: throw DataNotFoundException(DATA_NOT_FOUND, "브랜드 정보를 찾을 수 없습니다.")
    }

    private fun validateNoAssociatedProducts(brandId: Long) {
        if (productJpaRepository.findAllByBrandId(brandId).isNotEmpty()) {
            throw PolicyException(POLICY_VIOLATION, "해당 브랜드에 속한 상품이 존재하여 삭제할 수 없습니다.")
        }
    }
}
