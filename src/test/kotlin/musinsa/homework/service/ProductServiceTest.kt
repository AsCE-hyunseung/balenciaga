package musinsa.homework.service

import com.appmattus.kotlinfixture.kotlinFixture
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.every
import io.mockk.mockk
import musinsa.homework.exception.DataNotFoundException
import musinsa.homework.exception.ParameterInvalidException
import musinsa.homework.repository.BrandJpaRepository
import musinsa.homework.repository.CategoryJpaRepository
import musinsa.homework.repository.ProductJpaRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.springframework.data.repository.findByIdOrNull

@Execution(ExecutionMode.CONCURRENT)
class ProductServiceTest {
    private val productJpaRepository = mockk<ProductJpaRepository>()
    private val brandJpaRepository = mockk<BrandJpaRepository>()
    private val categoryJpaRepository = mockk<CategoryJpaRepository>()

    private val sut = ProductService(
        productJpaRepository = productJpaRepository,
        brandJpaRepository = brandJpaRepository,
        categoryJpaRepository = categoryJpaRepository
    )

    private val kotlinFixture = kotlinFixture()

    @Test
    fun `createProduct 가격이 음수면 에러를 낸다`() {
        // given
        val price = -1
        // when, then
        shouldThrow<ParameterInvalidException> {
            sut.createProduct(price, kotlinFixture(), kotlinFixture())
        }
    }

    @Test
    fun `updateProduct 가격이 음수면 에러를 낸다`() {
        // given
        val productId = kotlinFixture<Long>()
        val price = -1
        // when, then
        shouldThrow<ParameterInvalidException> {
            sut.updateProduct(productId, price, kotlinFixture())
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
