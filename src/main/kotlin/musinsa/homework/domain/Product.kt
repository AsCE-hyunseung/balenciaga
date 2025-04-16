package musinsa.homework.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import musinsa.homework.exception.ErrorCode
import musinsa.homework.exception.ParameterInvalidException
import musinsa.homework.listener.ProductListener

@Entity
@Table(name = "products")
@EntityListeners(ProductListener::class)
class Product(
    @Column(nullable = false) var price: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    val brand: Brand,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    var category: Category,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) {
    init {
        if (price < 0) {
            throw ParameterInvalidException(ErrorCode.INVALID_PARAMETER, "가격은 음수일 수 없습니다.")
        }
    }

    fun update(category: Category?, price: Int?) {
        category?.let {
            this.category = it
        }
        price?.let {
            if (it < 0) {
                throw ParameterInvalidException(ErrorCode.INVALID_PARAMETER, "가격은 음수일 수 없습니다.")
            }
            this.price = it
        }
    }
}
