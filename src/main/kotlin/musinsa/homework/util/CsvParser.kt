package musinsa.homework.util

import musinsa.homework.domain.Brand
import musinsa.homework.domain.Category
import musinsa.homework.domain.Product
import java.io.File

class CsvParser {
    /**
     * CSV 파일 경로를 받아서, 브랜드, 카테고리, 상품 리스트를 반환합니다.
     */
    fun parseCsvData(filePath: String): Triple<List<Brand>, List<Category>, List<Product>> {
        val file = File(filePath)
        if (!file.exists()) return Triple(emptyList(), emptyList(), emptyList())

        val lines = file.readLines()
        if (lines.isEmpty()) return Triple(emptyList(), emptyList(), emptyList())

        // 첫 줄은 헤더: 첫 컬럼은 "브랜드", 나머지는 카테고리 이름
        val header = lines.first().split(",").map { it.trim() }
        // 카테고리 생성: 인덱스 0은 브랜드이므로, 인덱스 1부터 사용
        val categories: List<Category> = header.drop(1).mapIndexed { index, name ->
            Category(id = (index + 1).toLong(), name = name)
        }

        val brands = mutableListOf<Brand>()
        val products = mutableListOf<Product>()

        // Product ID를 부여하기 위한 카운터
        var productIdCounter = 1L

        // 각 데이터 행 처리: 각 행은 한 브랜드의 정보를 담고 있음
        // 데이터 행 처리 시, 브랜드 ID는 CSV의 행 순서대로 부여 (1번부터)
        lines.drop(1).forEachIndexed { rowIndex, line ->
            val columns = line.split(",").map { it.trim() }
            if (columns.isEmpty()) return@forEachIndexed

            // 브랜드 정보: 각 행의 첫 번째 컬럼
            val brandName = columns[0]
            val brandId = (rowIndex + 1).toLong() // 브랜드 순번을 ID로 사용
            brands.add(Brand(id = brandId, name = brandName))

            // 두 번째 컬럼부터는 각 카테고리의 상품 가격 정보
            columns.drop(1).forEachIndexed { catIndex, priceStr ->
                val price = priceStr.toIntOrNull() ?: 0
                // 카테고리 ID는 header에서 생성한 순서와 동일 (catIndex + 1)
                val categoryId = (catIndex + 1).toLong()
                // 각 상품(Product)에 product id, 브랜드 id, 카테고리 id, 가격 정보를 저장
                products.add(
                    Product(
                        id = productIdCounter++,
                        brandId = brandId,
                        categoryId = categoryId,
                        price = price
                    )
                )
            }
        }

        return Triple(brands, categories, products)
    }
}
