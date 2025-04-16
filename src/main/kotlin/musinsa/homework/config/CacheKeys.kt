package musinsa.homework.config

object CacheKeys {
    // 카테고리 전체 캐시키
    const val CATEGORY = "category"

    // 카테고리별 최저가 상품 ID 캐시키
    const val CATEGORY_LOWEST_PRICE_PRODUCT_ID = "category-lowest-price-product-id"

    // 카테고리별 최고가 상품 ID 캐시키
    const val CATEGORY_HIGHEST_PRICE_PRODUCT_ID = "category-highest-price-product-id"

    // 상품 금액 총 합이 최저인 브랜드 ID 캐시키
    const val LOWEST_PRICE_BRAND_ID = "lowest-price-brand-id"
}
