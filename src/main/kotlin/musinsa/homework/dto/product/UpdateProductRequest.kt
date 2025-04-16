package musinsa.homework.dto.product

// 상품의 brand 수정은 지원하지 않는다.
data class UpdateProductRequest(
    val price: Int?,
    val categoryId: Long?
)
