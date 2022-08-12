package com.example.appkotlin

import com.example.appkotlin.payload.*
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
        return ResponseEntity.ok(
            Response(
                "get by id",
                200,
                categoryRepository.findById(id).orElseThrow { throw CategoryFoundException("this id : $id not found") }
            )
        )

    }

    override fun delete(id: Long): ResponseEntity<Response> {
        categoryRepository.findById(id).orElseThrow { throw CategoryFoundException("this id : $id not found") }

        categoryRepository.deleteById(id)
        return ResponseEntity.ok(Response("Successfully delete", 200))

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
            categories.add(
                categoryRepository.findById(it)
                    .orElseThrow { throw CategoryFoundException("This $it id category not found") }
            )
        }

        val au = authorRepository.findById(dto.authorId)
            .orElseThrow { throw AuthorNotFoundException("This ${dto.authorId} not found") }

        val save = bookRepository.save(Book(dto.name, categories, au))
        return ResponseEntity.ok(
            Response(
                "add book",
                200,
                BookResponse.toResponse(save)
            )
        )

    }

    override fun getById(id: Long): ResponseEntity<Response> {
        val book = bookRepository.findById(id).orElseThrow { throw BookNotFoundException("$id book not fouund") }
        return ResponseEntity.ok(Response("get by id", 200, BookResponse.toResponse(book)))

    }

    override fun update(id: Long, dto: BookDto): ResponseEntity<Response> {
        val categories: MutableList<Category> = mutableListOf()

        dto.categoryIds.forEach {
            categories.add(
                categoryRepository.findById(it)
                    .orElseThrow { throw CategoryFoundException("This $it id category not found") }
            )
        }

        val au = authorRepository.findById(dto.authorId)
            .orElseThrow { throw AuthorNotFoundException("This ${dto.authorId} not found") }

        val save = bookRepository.save(Book(dto.name, categories, au, id))
        return ResponseEntity.ok(
            Response(
                "add book",
                200,
                BookResponse.toResponse(save)
            )
        )
    }

    override fun delete(id: Long): ResponseEntity<Response> {
        bookRepository.findById(id).orElseThrow { throw BookNotFoundException("$id not foubd book") }
        bookRepository.deleteById(id)
        return ResponseEntity.ok(Response("get by id", 200))

    }
}
