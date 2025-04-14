package musinsa.homework.domain

import jakarta.persistence.*

@Entity
@Table(name = "products")
class Product(
    @Column(nullable = false) var price: Int,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "brand_id") val brand: Brand,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "category_id") var category: Category,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) {
    fun update(category: Category, price: Int) {
        this.category = category
        this.price = price
    }
}
