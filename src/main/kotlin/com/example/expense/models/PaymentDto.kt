package com.example.expense.models

import io.micronaut.serde.annotation.Serdeable

@Serdeable
class PaymentDto(val id: Long?, var name: String, var amount: Double, var user: UserDto, var expense: ExpenseDto) {
}
