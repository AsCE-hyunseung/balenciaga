package musinsa.homework.controller

import musinsa.homework.service.BrandService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/brands")
class BrandController(
    private val brandService: BrandService,
) {
    // 1. 브랜드 추가
    // 2. 브랜드 업데이트(이름?)
    // 3. 브랜드 삭제
}