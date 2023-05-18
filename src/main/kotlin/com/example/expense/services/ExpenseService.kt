package com.example.expense.services

import com.example.expense.entities.Expense
import com.example.expense.models.ExpenseDto
import com.example.expense.repositories.ExpenseRepository
import com.example.expense.repositories.GroupRepository
import jakarta.inject.Singleton
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Singleton
class ExpenseService(
    private val expenseRepository: ExpenseRepository,
    private val groupRepository: GroupRepository,
    private val commonService: CommonService
) {
    fun getAll(): Flux<ExpenseDto> {
        return expenseRepository.findAll().map { commonService.toExpenseDto(it) }
    }

    fun getExpenseById(id: Long): Mono<ExpenseDto?> {
        return expenseRepository.findById(id).map { commonService.toExpenseDto(it) }
    }

    fun saveExpense(expenseDto: ExpenseDto): Mono<ExpenseDto?> {
        val group = groupRepository.findById(expenseDto.group!!.id)
        val expense = Expense(expenseDto.name, expenseDto.description, group.block())
        return expenseRepository.save(expense).map { commonService.toExpenseDto(it!!) }
    }

    fun deleteExpenseById(id: Long): Mono<Long> {
        return expenseRepository.deleteById(id)
    }

    fun updateExpense(expenseDto: ExpenseDto): Mono<ExpenseDto?> {
        val group = groupRepository.findById(expenseDto.group!!.id)
        val expense = Expense(expenseDto.name, expenseDto.description, group.block())
        expense.id = expenseDto.id!!
        return expenseRepository.update(expense).map { commonService.toExpenseDto(it!!) }
    }
}
