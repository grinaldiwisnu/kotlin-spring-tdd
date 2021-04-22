package com.example.todos.demo.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest {
    private val mockBankDataSource: MockBankDataSource = MockBankDataSource()

    @Test
    fun `should provide a collection of banks`() {
        // given


        // when
        val banks = mockBankDataSource.retriveBanks()

        // then
        assertThat(banks).isNotEmpty
        assertThat(banks.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data`() {
        // given


        // when
        val banks = mockBankDataSource.retriveBanks()

        // then
        assertThat(banks).allMatch { it.accountNumber.isNotBlank() }
        assertThat(banks).anyMatch { it.transactionFee != 0 }
        assertThat(banks).anyMatch { it.transactionFee != 0 }
    }
}