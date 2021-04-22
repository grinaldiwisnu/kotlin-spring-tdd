package com.example.todos.demo.service

import com.example.todos.demo.datasource.BankDataSource
import com.example.todos.demo.model.Bank
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class BankService(@Qualifier("network") private val bankDataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> = bankDataSource.retriveBanks()
    fun getBank(accountNumber: String): Bank = bankDataSource.retriveBank(accountNumber)
    fun addBank(bank: Bank): Bank = bankDataSource.createBank(bank)
    fun updateBank(bank: Bank): Bank = bankDataSource.updateBank(bank)
    fun deleteBank(accountNumber: String): Bank = bankDataSource.deleteBank(accountNumber)
}