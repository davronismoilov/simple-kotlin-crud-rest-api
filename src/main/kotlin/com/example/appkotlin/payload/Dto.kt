package com.example.appkotlin.payload

import com.example.appkotlin.Author
import com.example.appkotlin.Book
import com.example.appkotlin.Category
import java.util.Date

data class CategoryDto(
    var name: String,
    var parentCategory: Long
) {
    fun toDto(category: Category): CategoryDto? {
        return category.parentCategory?.id?.let { CategoryDto(category.name, it) }
    }
}

data class BookResponse(
    var name: String,
    var author: String,
    var categories: List<String>?
) {
    companion object {
        fun toResponse(book: Book) = book.run {
            BookResponse(
                name, author?.name ?: "",
                categories?.map { it.name }
            )
        }
    }
}


data class BookDto(
    var name: String,
    var authorId: Long,
    var categoryIds: List<Long>
)

data class AuthorDto(
    var email: String,
    var name: String,
    var phoneNumber: String

) {
    fun toEntity(): Author {
        return Author(name, phoneNumber, email)
    }
}


data class Response(
    var message: String,
    var status: Int,
    var data: Any? = null
)

data class ErrorMessageResponse(
    var status: Int,
    var dateTime: Date,
    var message: String?,
    var cause: Throwable?
)
