package musinsa.homework.controller

import musinsa.homework.dto.product.CreateProductRequest
import musinsa.homework.dto.product.ProductDto
import musinsa.homework.dto.product.UpdateProductRequest
import musinsa.homework.service.ProductService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productService: ProductService
) {
    // 상품 추가 api
    @PostMapping
    fun createProduct(@RequestBody request: CreateProductRequest): ProductDto {
        return productService.createProduct(request.price, request.brandId, request.categoryId)
    }

    // 상품 정보 업데이트 api
    @PatchMapping("/{productId}")
    fun updateProduct(@PathVariable productId: Long, @RequestBody request: UpdateProductRequest): ProductDto {
        return productService.updateProduct(productId, request.price, request.categoryId)
    }

    // 상품 삭제 api
    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable productId: Long): Boolean {
        return productService.deleteProduct(productId)
    }
}
