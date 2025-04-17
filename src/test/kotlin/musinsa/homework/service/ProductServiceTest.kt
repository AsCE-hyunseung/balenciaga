package musinsa.homework.service

import com.appmattus.kotlinfixture.kotlinFixture
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.every
import io.mockk.mockk
import musinsa.homework.domain.Brand
import musinsa.homework.domain.Category
import musinsa.homework.domain.Product
import musinsa.homework.exception.DataNotFoundException
import musinsa.homework.exception.ParameterInvalidException
import musinsa.homework.repository.BrandJpaRepository
import musinsa.homework.repository.ProductJpaRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.springframework.data.repository.findByIdOrNull

@Execution(ExecutionMode.CONCURRENT)
class ProductServiceTest {
    private val productJpaRepository = mockk<ProductJpaRepository>()
    private val brandJpaRepository = mockk<BrandJpaRepository>()
    private val categoryCacheService = mockk<CategoryCacheService>()

    private val sut = ProductService(
        productJpaRepository = productJpaRepository,
        brandJpaRepository = brandJpaRepository,
        categoryCacheService = categoryCacheService
    )

    private val kotlinFixture = kotlinFixture()

    @Test
    fun `createProduct 가격이 음수면 에러를 낸다`() {
        // given
        val brandId = kotlinFixture<Long>()
        val categoryId = kotlinFixture<Long>()
        val price = -1
        every { brandJpaRepository.findByIdOrNull(brandId) } returns kotlinFixture<Brand>()
        every { categoryCacheService.getAllCategories() } returns listOf(
            kotlinFixture<Category> {
                property(Category::id) { categoryId }
            }
        )
        // when, then
        shouldThrow<ParameterInvalidException> {
            sut.createProduct(price, brandId, categoryId)
        }
    }

    @Test
    fun `updateProduct input이 productId만 들어왔을때 에러를 낸다`() {
        // given, when, then
        shouldThrow<ParameterInvalidException> {
            sut.updateProduct(1L, null, null)
        }
    }

    @Test
    fun `updateProduct 가격이 음수면 에러를 낸다`() {
        // given
        val productId = kotlinFixture<Long>()
        val category = kotlinFixture<Category> {
            property(Category::id) { kotlinFixture<Long>() }
        }
        val price = -1
        every { productJpaRepository.findByIdOrNull(productId) } returns kotlinFixture<Product> {
            property(Product::price) { 10000 }
            property(Product::category) { category }
        }
        every { categoryCacheService.getAllCategories() } returns listOf(category)

        // when, then
        shouldThrow<ParameterInvalidException> {
            sut.updateProduct(productId, price, category.id)
        }
    }

    @Test
    fun `updateProduct 데이터 조회가 안되면 에러를 낸다`() {
        // given
        val productId = kotlinFixture<Long>()
        val price = 100
        every { productJpaRepository.findByIdOrNull(productId) } returns null
        // when, then
        shouldThrow<DataNotFoundException> {
            sut.updateProduct(productId, price, kotlinFixture())
        }
    }

    @Test
    fun `deleteProduct 데이터 조회가 안되면 에러를 낸다`() {
        // given
        val productId = kotlinFixture<Long>()
        every { productJpaRepository.findByIdOrNull(productId) } returns null
        // when, then
        shouldThrow<DataNotFoundException> {
            sut.deleteProduct(productId)
        }
    }
}
