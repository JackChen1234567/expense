package com.example.expense.repositories

import com.example.expense.entities.Expense
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.reactive.ReactorCrudRepository

@Repository
abstract class ExpenseRepository : ReactorCrudRepository<Expense, Long> {
}
