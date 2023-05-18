package com.example.expense.repositories

import com.example.expense.entities.Payment
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import reactor.core.publisher.Flux

@Repository
interface PaymentRepository : ReactorCrudRepository<Payment, Long> {
    @Query("FROM Payment p WHERE p.user.id = :uid and p.expense.id=:eid")
    fun getPaymentsByUserAndExpense(uid: Long, eid: Long): Flux<Payment>
}
