package musinsa.homework.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(DataNotFoundException::class)
    fun handleDataNotFoundException(e: DataNotFoundException): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.NOT_FOUND

        val errorResponse = ErrorResponse(
            code = e.code.toString(),
            message = e.message ?: "정보를 찾을 수 없습니다.",
            status = status.value()
        )
        return ResponseEntity(errorResponse, status)
    }

    @ExceptionHandler(PolicyException::class)
    fun handlePolicyException(e: PolicyException): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.BAD_REQUEST

        val errorResponse = ErrorResponse(
            code = e.code.toString(),
            message = e.message ?: "정책 위반입니다.",
            status = status.value()
        )
        return ResponseEntity(errorResponse, status)
    }

    @ExceptionHandler(Exception::class)
    fun handleCommonException(e: Exception): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.INTERNAL_SERVER_ERROR

        val errorResponse = ErrorResponse(
            code = ErrorCode.INTERNAL_SERVER_ERROR.toString(),
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

data class DataNotFoundException(val code: ErrorCode, val msg: String) : RuntimeException(msg)
data class PolicyException(val code: ErrorCode, val msg: String) : RuntimeException(msg)
data class ParameterInvalidException(val errorCode: ErrorCode, val msg: String) : RuntimeException(msg)

enum class ErrorCode {
    DATA_NOT_FOUND,
    POLICY_VIOLATION,
    INTERNAL_SERVER_ERROR,
    INVALID_PARAMETER
}
