package com.example.appkotlin

import com.example.appkotlin.payload.AuthorDto
import com.example.appkotlin.payload.BookDto
import com.example.appkotlin.payload.CategoryDto
import com.example.appkotlin.payload.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/v1/category")
class CategoryController(var categoryService: CategoryService) {

    @GetMapping("{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Response> {
        return categoryService.getById(id)
    }

    @PostMapping
    fun create(@RequestBody categoryDto: CategoryDto): ResponseEntity<Response> {
        return categoryService.create(categoryDto)
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody categoryDto: CategoryDto): ResponseEntity<Response> {
        return categoryService.update(id, categoryDto)
    }


    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Response> {
        return categoryService.delete(id)
    }


}


@RestController
@RequestMapping("api/v1/author")
class AuthorController(var authorService: AuthorService) {

    @GetMapping("{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Response> {
        return authorService.getById(id)
    }

    @PostMapping
    fun create(@RequestBody authorDto: AuthorDto): ResponseEntity<Response> {
        return authorService.create(authorDto)
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody authorDto: AuthorDto): ResponseEntity<Response> {
        return authorService.update(id, authorDto)
    }


    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Response> {
        return authorService.delete(id)
    }

}



@RestController
@RequestMapping("api/v1/book")
class BookController(var bookservice: BookService) {

    @GetMapping("{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Response> {
        return bookservice.getById(id)
    }

    @PostMapping
    fun create(@RequestBody bookdto: BookDto): ResponseEntity<Response> {
        return bookservice.create(bookdto)
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody bookdto: BookDto): ResponseEntity<Response> {
        return bookservice.update(id, bookdto)
    }


    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Response> {
        return bookservice.delete(id)
    }

}
