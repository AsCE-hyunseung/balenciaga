package musinsa.homework.config

import jakarta.annotation.PostConstruct
import musinsa.homework.repository.BrandJpaRepository
import musinsa.homework.repository.CategoryJpaRepository
import musinsa.homework.repository.ProductJpaRepository
import musinsa.homework.util.CsvParser
import musinsa.homework.util.logger
import org.springframework.stereotype.Component

@Component
class CsvDataLoader(
    private val brandJpaRepository: BrandJpaRepository,
    private val categoryJpaRepository: CategoryJpaRepository,
    private val productJpaRepository: ProductJpaRepository
) {
    private val logger = logger()

    @PostConstruct
    fun loadCsvData() {
        val dataCsv = this::class.java.getResource("/data.csv")
        val (brands, categories, products) = CsvParser().parseCsvData(dataCsv.path)
        brandJpaRepository.saveAll(brands)
        categoryJpaRepository.saveAll(categories)
        productJpaRepository.saveAll(products)
        logger.info("CSV data loaded successfully")
    }
}
