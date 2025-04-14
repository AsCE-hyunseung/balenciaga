package musinsa.homework.service

import musinsa.homework.domain.Product
import musinsa.homework.dto.product.ProductDto
import musinsa.homework.exception.DataNotFoundException
import musinsa.homework.exception.ErrorCode
import musinsa.homework.repository.BrandJpaRepository
import musinsa.homework.repository.CategoryJpaRepository
import musinsa.homework.repository.ProductJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productJpaRepository: ProductJpaRepository,
    private val brandJpaRepository: BrandJpaRepository,
    private val categoryJpaRepository: CategoryJpaRepository
) {
    @Transactional
    fun createProduct(price: Int, brandId: Long, categoryId: Long): ProductDto {
        validateBrandAndCategory(brandId, categoryId)
        val product = Product(price = price, brandId = brandId, categoryId = categoryId)
        productJpaRepository.save(product)
        return ProductDto.from(product)
    }

    @Transactional
    fun updateProduct(productId: Long, price: Int, categoryId: Long): ProductDto {
        val product = findProductById(productId)
        product.update(price = price, categoryId = categoryId)
        return ProductDto.from(product)
    }

    @Transactional
    fun deleteProduct(productId: Long): Boolean {
        findProductById(productId)
        productJpaRepository.deleteById(productId)
        return true
    }

    fun findProductById(productId: Long): Product {
        return productJpaRepository.findByIdOrNull(productId)
            ?: throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "상품 정보를 찾을 수 없습니다.")
    }

    fun validateBrandAndCategory(brandId: Long, categoryId: Long) {
        if (!brandJpaRepository.existsById(brandId)) {
            throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "브랜드 정보를 찾을 수 없습니다.")
        }
        if (!categoryJpaRepository.existsById(categoryId)) {
            throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "카테고리 정보를 찾을 수 없습니다.")
        }
    }
}
