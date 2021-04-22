package com.example.todos.demo.datasource.network

import com.example.todos.demo.datasource.BankDataSource
import com.example.todos.demo.model.Bank
import org.springframework.stereotype.Repository

@Repository("network")
class NetworkDataSource : BankDataSource {
    val banks = mutableListOf(
        Bank("1234", 3.14, 17),
        Bank("1010", 3.14, 17),
        Bank("5123", 3.14, 17)
    )

    override fun retriveBanks(): Collection<Bank> = banks

    override fun retriveBank(accountNumber: String): Bank =
        banks.firstOrNull { it.accountNumber == accountNumber }
            ?: throw NoSuchMethodError("Could not find a bank with account number $accountNumber")

    override fun createBank(bank: Bank): Bank {
        if (banks.any { it.accountNumber == bank.accountNumber }) {
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already exist")
        }

        banks.add(bank)

        return bank
    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank = banks.firstOrNull { it.accountNumber == bank.accountNumber }
            ?: throw NoSuchElementException("Could not find the account number ${bank.accountNumber}")

        banks.remove(currentBank)
        banks.add(bank)

        return bank
    }

    override fun deleteBank(accountNumber: String): Bank {
        val currentBank = banks.firstOrNull { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find the account number $accountNumber")

        banks.remove(currentBank)

        return currentBank
    }
}