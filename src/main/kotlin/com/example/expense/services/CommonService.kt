package com.example.expense.services

import com.example.expense.entities.Expense
import com.example.expense.entities.Payment
import com.example.expense.entities.User
import com.example.expense.entities.UserGroup
import com.example.expense.models.ExpenseDto
import com.example.expense.models.GroupDto
import com.example.expense.models.PaymentDto
import com.example.expense.models.UserDto
import jakarta.inject.Singleton

@Singleton
class CommonService {
    fun toUserDto(user: User): UserDto {

        val userDto = UserDto(user.id, user.name)
        val groupDto = GroupDto(user.userGroup!!.id, user.userGroup!!.name)
        userDto.userGroup = groupDto
        return userDto
    }

    fun toGroupDto(userGroup: UserGroup): GroupDto {

        val group = GroupDto(userGroup.id, userGroup.name)
        userGroup.users.forEach { user: User -> group.addUser(toUserDtoWithoutGroup(user)) }
        return group
    }

    fun toUserDtoWithoutGroup(user: User): UserDto {
        return UserDto(user.id, user.name)
    }

    fun toExpenseDto(expense: Expense): ExpenseDto {
        val expenseDto = ExpenseDto(expense.id, expense.name, expense.description)
        expenseDto.group = toGroupDto(expense.userGroup!!)
        return expenseDto
    }

    fun toPaymentDto(payment: Payment): PaymentDto {
        return PaymentDto(
            payment.id,
            payment.name,
            payment.amount,
            toUserDtoWithoutGroup(payment.user!!),
            toExpenseDto(payment.expense!!)
        )
    }

}
