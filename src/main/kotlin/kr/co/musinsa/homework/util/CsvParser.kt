package kr.co.musinsa.homework.util

import java.io.File

class CsvParser {
    fun parseProducts(filePath: String): List<Product> {
        val result = mutableListOf<Product>()
        val file = File(filePath)
        if (!file.exists()) return emptyList()

        file.readLines().drop(1).forEach { line ->
            val columns = line.split(",")
            if (columns.size < 4) return@forEach

            val id = columns[0].trim().toLong()
            val name = columns[1].trim()
            val price = columns[2].trim().toLong()
            val stock = columns[3].trim().toInt()

            result.add(
                Product(
                    id = id,
                    name = name,
                    price = price,
                    stock = stock
                )
            )
        }
        return result
    }
}
