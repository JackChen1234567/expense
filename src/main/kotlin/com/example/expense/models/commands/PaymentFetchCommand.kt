package com.example.expense.models.commands

import io.micronaut.serde.annotation.Serdeable

@Serdeable
class PaymentFetchCommand(val userId: Long, val expenseId: Long) {
}
