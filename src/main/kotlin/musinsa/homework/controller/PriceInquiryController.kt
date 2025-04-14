package musinsa.homework.controller

import musinsa.homework.dto.priceinquiry.LowestAndHighestProductDto
import musinsa.homework.service.PriceInquiryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/price-inquiry")
class PriceInquiryController(
    private val priceInquiryService: PriceInquiryService
) {

    @GetMapping("/lowest-and-highest-products")
    // 카테고리명으로 최저 상품, 최고 상품 정보 조회
    fun getLowestAndHighestProductsByCategoryName(categoryName: String): List<LowestAndHighestProductDto> {
        return priceInquiryService.getLowestAndHighestProductsByCategoryName(categoryName)
    }
}