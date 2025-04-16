package musinsa.homework.service

import musinsa.homework.domain.Brand
import musinsa.homework.domain.Category
import musinsa.homework.domain.Product
import musinsa.homework.dto.product.ProductDto
import musinsa.homework.exception.DataNotFoundException
import musinsa.homework.exception.ErrorCode
import musinsa.homework.exception.ParameterInvalidException
import musinsa.homework.repository.BrandJpaRepository
import musinsa.homework.repository.ProductJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productJpaRepository: ProductJpaRepository,
    private val brandJpaRepository: BrandJpaRepository,
    private val categoryCacheService: CategoryCacheService
) {
    fun createProduct(price: Int, brandId: Long, categoryId: Long): ProductDto {
        val brand = findBrandById(brandId)
        val category = findCategoryById(categoryId)
        val product = Product(price = price, brand = brand, category = category)
        productJpaRepository.save(product)
        return ProductDto.from(product)
    }

    @Transactional
    fun updateProduct(productId: Long, price: Int?, categoryId: Long?): ProductDto {
        if (price == null && categoryId == null) {
            throw ParameterInvalidException(ErrorCode.INVALID_PARAMETER, "가격 또는 카테고리 ID는 필수입니다.")
        }
        val product = findProductById(productId)
        val category = categoryId?.let { findCategoryById(it) }
        product.update(price = price, category = category)
        return ProductDto.from(product)
    }

    @Transactional
    fun deleteProduct(productId: Long): Boolean {
        findProductById(productId)
        productJpaRepository.deleteById(productId)
        return true
    }

    private fun findProductById(productId: Long): Product {
        return productJpaRepository.findByIdOrNull(productId)
            ?: throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "상품 정보를 찾을 수 없습니다.")
    }

    private fun findBrandById(brandId: Long): Brand {
        return brandJpaRepository.findByIdOrNull(brandId)
            ?: throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "브랜드 정보를 찾을 수 없습니다.")
    }

    private fun findCategoryById(categoryId: Long): Category {
        return categoryCacheService.getAllCategories().find { it.id == categoryId }
            ?: throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "카테고리 정보를 찾을 수 없습니다.")
    }
}
