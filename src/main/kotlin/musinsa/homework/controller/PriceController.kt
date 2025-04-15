package musinsa.homework.controller

import musinsa.homework.dto.price.CheapestBrandResponse
import musinsa.homework.dto.price.CheapestProductResponse
import musinsa.homework.service.PriceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/prices")
class PriceController(
    private val priceService: PriceService
) {
    // 카테고리별 최저 가격 브랜드, 상품 정보 조회 api
    @GetMapping("/cheapest-products")
    fun getCheapestProductsByCategory(): CheapestProductResponse {
        return priceService.getCheapestProductsByCategory()
    }

    // 단일 브랜드로 모든 상품의 가격 총합이 가장 낮은 브랜드의 상품 조회 api
    @GetMapping("/cheapest-brands")
    fun getCheapestBrands(): CheapestBrandResponse {
        return priceService.getCheapestBrands()
    }
}
