package musinsa.homework.controller

import musinsa.homework.dto.price.LowestAndHighestProductResponse
import musinsa.homework.service.PriceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/categories")
class CategoryController(
    private val priceService: PriceService
) {
    // 카테고리명으로 최저 가격 상품, 최고 가격 상품 정보 조회
    @GetMapping("{categoryName}/price-range")
    fun getLowestAndHighestPriceProductsByCategoryName(@PathVariable categoryName: String): LowestAndHighestProductResponse {
        return priceService.getLowestAndHighestPriceProductsByCategoryName(categoryName)
    }
}
