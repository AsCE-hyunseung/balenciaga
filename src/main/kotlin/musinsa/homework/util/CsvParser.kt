package musinsa.homework.util

import java.io.File

class CsvParser {
    fun parseData(filePath: String) {
        val file = File(filePath)
        if (!file.exists()) return
    }
}
