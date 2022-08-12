package com.example.appkotlin

import javax.persistence.*

@Entity
class Category(
    @Column(nullable = false, unique = true)
    var name: String,

    @ManyToOne
    var parentCategory: Category? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)


@Entity
class Author(

    @Column(nullable = false, unique = true)
    var name: String,

    @Column(nullable = false, unique = true)
    var phoneNumber: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)


@Entity
class Book(
    var name: String,

    @ManyToMany
    var categories: MutableList<Category>? = null,

    @ManyToOne
    var author: Author? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

)


