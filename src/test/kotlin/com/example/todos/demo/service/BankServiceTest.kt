package com.example.todos.demo.service

import com.example.todos.demo.datasource.BankDataSource
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class BankServiceTest {
    private val bankDataSource: BankDataSource = mockk(relaxed = true)
    private val bankService: BankService = BankService(bankDataSource)

    @Test
    fun `should call its data source to retrive banks`() {
        // when
        val banks = bankService.getBanks()

        // then
        verify(exactly = 1) { bankDataSource.retriveBanks() }
    }
}