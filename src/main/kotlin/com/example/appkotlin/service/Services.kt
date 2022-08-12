package com.example.appkotlin

import com.example.appkotlin.payload.AuthorDto
import com.example.appkotlin.payload.BookDto
import com.example.appkotlin.payload.CategoryDto
import com.example.appkotlin.payload.Response
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

interface BaseService<T, R> {

    fun create(dto: T): R
    fun getById(id: Long): R
    fun update(id: Long, dto: T): R
    fun delete(id: Long): R
}

@Service
class CategoryService(
    var categoryRepository: CategoryRepository
) : BaseService<CategoryDto, ResponseEntity<Response>> {

    override fun create(dto: CategoryDto): ResponseEntity<Response> {
        val parentCategory = categoryRepository.findById(dto.parentCategory).orElse(null)

        val save = categoryRepository.save(Category(dto.name, parentCategory))
        return ResponseEntity.ok(dto.toDto(save)?.let { Response("Add category", 200, it) })
    }

    override fun getById(id: Long): ResponseEntity<Response> {
        val category = categoryRepository.findById(id).orElse(null)

        return if (category == null) {
            ResponseEntity.ok(Response("Not found", 404))
        } else {
            ResponseEntity.ok(Response("get by id", 200, category))
        }
    }

    override fun delete(id: Long): ResponseEntity<Response> {
        val category = categoryRepository.findById(id).orElse(null)

        return if (category == null) {
            ResponseEntity.ok(Response("Not found", 404))
        } else {
            categoryRepository.deleteById(id)
            ResponseEntity.ok(Response("Successfully delete", 200))
        }
    }

    override fun update(id: Long, dto: CategoryDto): ResponseEntity<Response> {
        val parentCategory = categoryRepository.findById(dto.parentCategory).orElse(null)

        val save = categoryRepository.save(Category(dto.name, parentCategory, id))
        return ResponseEntity.ok(dto.toDto(save)?.let { Response("update category", 200, it) })
    }

}

@Service
class AuthorService(
    var authorRepository: AuthorRepository
) : BaseService<AuthorDto, ResponseEntity<Response>> {

    override fun create(dto: AuthorDto): ResponseEntity<Response> {
        val author = dto.toEntity()
        return ResponseEntity.ok(
            Response(
                "Add author",
                200,
                authorRepository.save(author)
            )
        )

    }

    override fun getById(id: Long): ResponseEntity<Response> {
        val author = authorRepository.findById(id).orElse(null)

        return if (author == null) {
            ResponseEntity.ok(Response("Not found", 404))
        } else {
            ResponseEntity.ok(Response("get by id", 200, author))
        }
    }

    override fun update(id: Long, dto: AuthorDto): ResponseEntity<Response> {
        val author = dto.toEntity()
        author.id = id
        return ResponseEntity.ok(
            Response(
                "Add author",
                200,
                authorRepository.save(author)
            )
        )
    }

    override fun delete(id: Long): ResponseEntity<Response> {
        val author = authorRepository.findById(id).orElse(null)

        return if (author == null) {
            ResponseEntity.ok(Response("Not found", 404))
        } else {
            authorRepository.deleteById(id)
            ResponseEntity.ok(Response("get by id", 200))
        }
    }
}

@Service
class BookService(
    var bookRepository: BookRepository,
    var categoryRepository: CategoryRepository,
    var authorRepository: AuthorRepository
) : BaseService<BookDto, ResponseEntity<Response>> {

    override fun create(dto: BookDto): ResponseEntity<Response> {
        val categories: MutableList<Category> = mutableListOf()

        dto.categoryIds.forEach {
            val findById = categoryRepository.findById(it)
            if (findById.isPresent) {
                categories.add(findById.get())
            }
        }

        val au = authorRepository.findById(dto.authorId).orElse(null)

        bookRepository.save(Book(dto.name, categories, au))
        return ResponseEntity.ok(
            Response(
                "add book",
                200,
            )
        )

    }

    override fun getById(id: Long): ResponseEntity<Response> {
        val book = bookRepository.findById(id).orElse(null)

        return if (book == null) {
            ResponseEntity.ok(Response("Not found", 404))
        } else {
            ResponseEntity.ok(Response("get by id", 200, book))
        }
    }

    override fun update(id: Long, dto: BookDto): ResponseEntity<Response> {
        val categories: MutableList<Category> = mutableListOf()

        dto.categoryIds.forEach {
            val findById = categoryRepository.findById(it)
            if (findById.isPresent) {
                categories.add(findById.get())
            }
        }

        val au = authorRepository.findById(dto.authorId).orElse(null)

        bookRepository.save(Book(dto.name, categories, au, id))
        return ResponseEntity.ok(
            Response(
                "add book",
                200,
            )
        )
    }

    override fun delete(id: Long): ResponseEntity<Response> {
        val book = bookRepository.findById(id).orElse(null)

        return if (book == null) {
            ResponseEntity.ok(Response("Not found", 404))
        } else {
            bookRepository.deleteById(id)
            ResponseEntity.ok(Response("get by id", 200, book))
        }
    }
}
