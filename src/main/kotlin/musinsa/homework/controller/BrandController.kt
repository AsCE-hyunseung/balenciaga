package musinsa.homework.controller

import jakarta.validation.Valid
import musinsa.homework.dto.brand.BrandDto
import musinsa.homework.dto.brand.CreateBrandRequest
import musinsa.homework.dto.brand.UpdateBrandRequest
import musinsa.homework.service.BrandService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/brands")
class BrandController(
    private val brandService: BrandService
) {
    /**
     * 브랜드 추가 api
     */
    @PostMapping
    fun createBrand(
        @RequestBody @Valid
        request: CreateBrandRequest
    ): ResponseEntity<BrandDto> {
        val created = brandService.createBrand(request.brandName)
        val uri = URI.create("/api/v1/brands/${created.brandId}")
        return ResponseEntity.created(uri).body(created)
    }

    /**
     * 브랜드 정보 업데이트 api
     */
    @PatchMapping("/{brandId}")
    fun updateBrand(@PathVariable brandId: Long, @RequestBody request: UpdateBrandRequest): ResponseEntity<BrandDto> {
        return ResponseEntity.ok(brandService.updateBrand(brandId, request.brandName))
    }

    /**
     * 브랜드 삭제 api
     */
    @DeleteMapping("/{brandId}")
    fun deleteBrand(@PathVariable brandId: Long): ResponseEntity<Void> {
        brandService.deleteBrand(brandId)
        return ResponseEntity.noContent().build()
    }
}
