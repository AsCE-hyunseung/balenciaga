package kr.co.musinsa.homework.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    // 2) 그 외 예상치 못한 에러 처리
    @ExceptionHandler(Exception::class)
    fun handleCommonException(e: Exception): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.INTERNAL_SERVER_ERROR

        val errorResponse = ErrorResponse(
            code = "INTERNAL_SERVER_ERROR",
            message = e.message ?: "Unknown error occurred.",
            status = status.value()
        )
        return ResponseEntity(errorResponse, status)
    }
}

data class ErrorResponse(
    val code: String,
    val message: String,
    val status: Int
)

class OutOfStockException(
    message: String? = null
) : RuntimeException(message)