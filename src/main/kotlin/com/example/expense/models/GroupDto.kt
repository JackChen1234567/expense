package com.example.expense.models

import io.micronaut.serde.annotation.Serdeable

@Serdeable
class GroupDto(val id: Long?, var name: String) {
    private val userList = mutableListOf<UserDto>()

    val users get() = userList.toList()

    fun addUser(user: UserDto) {
        userList += user
    }
}
