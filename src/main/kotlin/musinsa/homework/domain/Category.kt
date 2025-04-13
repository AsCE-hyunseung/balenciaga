package musinsa.homework.domain

import jakarta.persistence.*

@Entity
@Table(name = "categories")
class Category(
    @Column(nullable = false) val name: String,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0L,
)
