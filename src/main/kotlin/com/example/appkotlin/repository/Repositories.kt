package com.example.appkotlin

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {

}

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {

}

@Repository
interface AuthorRepository : JpaRepository<Author, Long> {

}
