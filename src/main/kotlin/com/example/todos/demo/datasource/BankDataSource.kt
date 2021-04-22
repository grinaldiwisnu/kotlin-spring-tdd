package com.example.todos.demo.datasource

import com.example.todos.demo.model.Bank

interface BankDataSource {
    fun retriveBanks(): Collection<Bank>
    fun retriveBank(accountNumber: String): Bank
    fun createBank(bank: Bank): Bank
    fun updateBank(bank: Bank): Bank
    fun deleteBank(accountNumber: String): Bank
}