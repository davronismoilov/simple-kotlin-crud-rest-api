package com.example.appkotlin

import java.util.function.Supplier

class BookNotFoundException(override val message: String) : RuntimeException() {}

class AuthorNotFoundException(override val message: String) : RuntimeException() {}

class CategoryFoundException(override val message: String) : RuntimeException()
