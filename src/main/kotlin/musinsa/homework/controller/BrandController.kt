package musinsa.homework.controller

import musinsa.homework.dto.BrandDto
import musinsa.homework.dto.CreateBrandRequest
import musinsa.homework.dto.UpdateBrandRequest
import musinsa.homework.service.BrandService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/brands")
class BrandController(
    private val brandService: BrandService
) {
    // 브랜드 추가 api
    @PostMapping
    fun createBrand(@RequestBody request: CreateBrandRequest): BrandDto {
        return brandService.createBrand(request.brandName)
    }

    // 브랜드 정보 업데이트 api
    @PatchMapping("/{brandId}")
    fun updateBrand(@PathVariable brandId: Long, @RequestBody request: UpdateBrandRequest): BrandDto {
        return brandService.updateBrand(brandId, request.brandName)
    }

    // 브랜드 삭제 api
    @DeleteMapping("/{brandId}")
    fun deleteBrand(@PathVariable brandId: Long): Boolean {
        return brandService.deleteBrand(brandId)
    }
}
