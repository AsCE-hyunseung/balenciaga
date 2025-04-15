package musinsa.homework.service

import musinsa.homework.domain.Brand
import musinsa.homework.domain.Category
import musinsa.homework.domain.Product
import musinsa.homework.dto.price.CategoryCheapestProduct
import musinsa.homework.dto.price.CheapestBrandInfo
import musinsa.homework.dto.price.CheapestBrandProduct
import musinsa.homework.dto.price.CheapestBrandResponse
import musinsa.homework.dto.price.CheapestProductResponse
import musinsa.homework.dto.price.LowestAndHighestProductResponse
import musinsa.homework.dto.product.ProductDto
import musinsa.homework.exception.DataNotFoundException
import musinsa.homework.exception.ErrorCode
import musinsa.homework.exception.PolicyException
import musinsa.homework.repository.BrandJpaRepository
import musinsa.homework.repository.CategoryJpaRepository
import musinsa.homework.repository.ProductJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PriceService(
    private val productJpaRepository: ProductJpaRepository,
    private val brandJpaRepository: BrandJpaRepository,
    private val categoryJpaRepository: CategoryJpaRepository
) {
    @Transactional(readOnly = true)
    fun getLowestAndHighestPriceProductsByCategoryName(categoryName: String): LowestAndHighestProductResponse {
        val category = findCategoryByName(categoryName)
        val products = productJpaRepository.findAllByCategoryIdOrderByIdAsc(category.id)

        val lowestProduct = products.minByOrNull { it.price }
        val highestProduct = products.maxByOrNull { it.price }

        if (lowestProduct == null && highestProduct == null) {
            throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "해당 카테고리의 상품이 존재하지 않습니다.")
        }
        if (lowestProduct?.id == highestProduct?.id) {
            throw PolicyException(ErrorCode.POLICY_VIOLATION, "최저가 상품과 최고가 상품이 동일합니다.")
        }

        return LowestAndHighestProductResponse(
            categoryId = category.id,
            categoryName = category.name,
            lowestProduct = ProductDto.from(lowestProduct!!),
            highestProduct = ProductDto.from(highestProduct!!)
        )
    }

    @Transactional(readOnly = true)
    fun getCheapestProductsByCategory(): CheapestProductResponse {
        val categories = findAllCategory()

        val cheapestProducts = categories.map { category ->
            val products = productJpaRepository.findAllByCategoryIdOrderByIdAsc(category.id)
            val cheapestProduct = products.minByOrNull { it.price }

            if (cheapestProduct == null) {
                CategoryCheapestProduct(category.name, null, null)
            } else {
                CategoryCheapestProduct.from(cheapestProduct)
            }
        }

        return CheapestProductResponse(
            products = cheapestProducts,
            totalPrice = cheapestProducts.sumOf { it.price ?: 0 }
        )
    }

    @Transactional(readOnly = true)
    fun getCheapestBrands(): CheapestBrandResponse {
        val brands = findAllBrand()
        val brandPriceMap = brands.associateWith { brand ->
            val products = findAllProductByBrandId(brand.id)
            val totalPrice = products.sumOf { it.price }
            totalPrice
        }
        val (cheapestBrand, totalPrice) = brandPriceMap.minBy { it.value }
        val cheapestBrandProducts = findAllProductByBrandId(cheapestBrand.id)
        return CheapestBrandResponse(
            lowestPrice = CheapestBrandInfo(
                brandName = cheapestBrand.name,
                categories = cheapestBrandProducts.map { CheapestBrandProduct.from(it) },
                totalPrice = totalPrice
            )
        )
    }

    private fun findCategoryByName(categoryName: String): Category {
        return categoryJpaRepository.findByName(categoryName)
            ?: throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "카테고리 정보를 찾을 수 없습니다.")
    }

    private fun findAllCategory(): List<Category> {
        val categories = categoryJpaRepository.findAll()
        if (categories.isEmpty()) {
            throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "카테고리 정보를 찾을 수 없습니다.")
        }
        return categories.sortedBy { it.id }
    }

    private fun findAllBrand(): List<Brand> {
        val brands = brandJpaRepository.findAll()
        if (brands.isEmpty()) {
            throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "브랜드 정보를 찾을 수 없습니다.")
        }
        return brands.sortedBy { it.id }
    }

    private fun findAllProductByBrandId(brandId: Long): List<Product> {
        val products = productJpaRepository.findAllByBrandIdOrderByCategoryIdAsc(brandId)
        if (products.isEmpty()) {
            throw DataNotFoundException(ErrorCode.DATA_NOT_FOUND, "해당 브랜드의 상품이 존재하지 않습니다.")
        }
        return products
    }
}
