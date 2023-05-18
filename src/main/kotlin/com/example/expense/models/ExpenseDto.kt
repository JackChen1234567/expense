package com.example.expense.models

import io.micronaut.serde.annotation.Serdeable

@Serdeable
class ExpenseDto(val id: Long?, var name: String, var description: String) {
    var group: GroupDto? = null
}
