package com.example.appkotlin

import com.example.appkotlin.payload.ErrorMessageResponse
import org.slf4j.LoggerFactory
import org.springframework.dao.InvalidDataAccessApiUsageException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.util.*


@RestControllerAdvice
class ApplicationExceptionHandler {

    @ExceptionHandler(value = [IllegalArgumentException::class, ClassCastException::class, IllegalStateException::class, InvalidDataAccessApiUsageException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(ex: IllegalArgumentException, request: WebRequest?): ErrorMessageResponse {
        return ErrorMessageResponse(
            HttpStatus.NOT_IMPLEMENTED.value(),
            Date(),
            ex.message,
            ex.cause
        )
    }

    @ExceptionHandler(value = [BookNotFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleFacultyNotFoundException(ex: BookNotFoundException, request: WebRequest?): ErrorMessageResponse {
        return ErrorMessageResponse(
            HttpStatus.NOT_FOUND.value(),
            Date(),
            "Book not found",
            ex.cause
        )
    }

    @ExceptionHandler(value = [AuthorNotFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleGroupNotFoundException(ex: AuthorNotFoundException, request: WebRequest?): ErrorMessageResponse {
        return ErrorMessageResponse(
            HttpStatus.NOT_FOUND.value(),
            Date(),
            "Category not found",
            ex.cause
        )
    }

    @ExceptionHandler(value = [CategoryFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleJournalNotFoundException(ex: CategoryFoundException, request: WebRequest?): ErrorMessageResponse {
        return ErrorMessageResponse(
            HttpStatus.NOT_FOUND.value(),
            Date(),
            "Product not found",
            ex.cause
        )
    }


}
