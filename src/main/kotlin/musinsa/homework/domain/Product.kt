package musinsa.homework.domain

import jakarta.persistence.*

@Entity
@Table(name = "products")
class Product(
    @Column(nullable = false) val brandId: Long,
    @Column(nullable = false) val categoryId: Long,
    @Column(nullable = false) val price: Int,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0L,
)
