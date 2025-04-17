package musinsa.homework.config

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class H2ForeignKeyDisabler(
    private val jdbcTemplate: JdbcTemplate
) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE")
    }
}
