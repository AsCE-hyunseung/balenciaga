package kr.co.musinsa.homework

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class HomeworkApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(HomeworkApplication::class.java)
        .run(*args)
}