package kr.co.musinsa.homework.config

import jakarta.annotation.PostConstruct
import kr.co.musinsa.homework.util.CsvParser
import kr.co.musinsa.homework.util.logger
import org.springframework.stereotype.Component

@Component
class CsvDataLoader {
    private val logger = logger()

    @PostConstruct
    fun loadCsvData() {
        val dataCsv = this::class.java.getResource("/data.csv")
        val dataList = CsvParser().parseProducts(dataCsv.path)

        // save brand
        // save product
        // save category
        logger.info("CSV data loaded successfully")
    }
}