package musinsa.homework.exception

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
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

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = e.bindingResult.fieldErrors
            .joinToString("; ") { "${it.field}: ${it.defaultMessage}" }
        val body = ErrorResponse(
            code = ErrorCode.INVALID_PARAMETER.name,
            message = errors,
            status = HttpStatus.BAD_REQUEST.value()
        )
        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(ParameterInvalidException::class)
    fun handleParameterInvalidException(e: ParameterInvalidException): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.BAD_REQUEST

        val errorResponse = ErrorResponse(
            code = e.errorCode.toString(),
            message = e.message ?: "잘못된 요청입니다.",
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

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        e: HttpMessageNotReadableException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.BAD_REQUEST

        val errorResponse = ErrorResponse(
            code = ErrorCode.INVALID_PARAMETER.toString(),
            message = e.message ?: "잘못된 요청 형식입니다.",
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
