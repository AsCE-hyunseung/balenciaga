package musinsa.homework

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class BalenciagaApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(BalenciagaApplication::class.java)
        .run(*args)
}