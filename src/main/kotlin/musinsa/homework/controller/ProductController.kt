package musinsa.homework.controller

import musinsa.homework.service.ProductService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productService: ProductService
) {
    // 1. 상품 추가
    // 2. 상품 업데이트(가격?)
    // 3. 상품 삭제
}
