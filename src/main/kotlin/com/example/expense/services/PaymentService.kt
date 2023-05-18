package com.example.expense.services

import com.example.expense.entities.Payment
import com.example.expense.models.PaymentDto
import com.example.expense.models.commands.PaymentFetchCommand
import com.example.expense.repositories.ExpenseRepository
import com.example.expense.repositories.PaymentRepository
import com.example.expense.repositories.UserRepository
import jakarta.inject.Singleton
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Singleton
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val userRepository: UserRepository,
    private val expenseRepository: ExpenseRepository,
    private val commonService: CommonService
) {
    fun getAll(): Flux<PaymentDto> {
        return paymentRepository.findAll().map { commonService.toPaymentDto(it) }
    }

    fun getPaymentById(id: Long): Mono<PaymentDto?> {
        return paymentRepository.findById(id).map { commonService.toPaymentDto(it) }
    }

    fun savePayment(paymentDto: PaymentDto): Mono<PaymentDto?> {
        val user = userRepository.findById(paymentDto.user.id)
        val expense = expenseRepository.findById(paymentDto.expense.id)
        val payment = Payment(paymentDto.name, paymentDto.amount, user.block(), expense.block())
        return paymentRepository.save(payment).map { commonService.toPaymentDto(it!!) }
    }

    fun deletePaymentById(id: Long): Mono<Long> {
        return paymentRepository.deleteById(id)
    }

    fun updatePayment(paymentDto: PaymentDto): Mono<PaymentDto?> {
        val user = userRepository.findById(paymentDto.user.id)
        val expense = expenseRepository.findById(paymentDto.expense.id)
        val payment = Payment(paymentDto.name, paymentDto.amount, user.block(), expense.block())
        payment.id = paymentDto.id!!
        return paymentRepository.update(payment).map { commonService.toPaymentDto(it!!) }
    }

    fun fetchPaymentsByUserAndExpense(command: PaymentFetchCommand): Flux<PaymentDto> {
        return paymentRepository.getPaymentsByUserAndExpense(command.userId, command.expenseId)
            .map { commonService.toPaymentDto(it) }
    }
}
