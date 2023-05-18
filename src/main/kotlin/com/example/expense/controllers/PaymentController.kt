package com.example.expense.controllers

import com.example.expense.models.PaymentDto
import com.example.expense.models.commands.PaymentFetchCommand
import com.example.expense.services.PaymentService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpHeaders
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI

@Controller("/payments")
class PaymentController(private val paymentService: PaymentService) {
    @Get
    @Produces(MediaType.APPLICATION_JSON)
    fun getPayments(): Flux<PaymentDto> {

        return paymentService.getAll()
    }

    @Get("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getPaymentById(@PathVariable("id") id: Long): Mono<PaymentDto?> {
        return paymentService.getPaymentById(id)
    }

    @Post
    fun savePayment(@Body paymentDto: PaymentDto): Mono<HttpResponse<PaymentDto>> {
        return paymentService.savePayment(paymentDto)
            .map {
                HttpResponse.created(it!!)
                    .headers { headers: MutableHttpHeaders ->
                        headers.location(location(it.id!!))
                    }
            }
    }

    @Post("/search")
    fun fetchPaymentsByUserAndExpense(@Body command: PaymentFetchCommand): Flux<PaymentDto> {
        return paymentService.fetchPaymentsByUserAndExpense(command)
    }

    @Delete("{id}")
    fun deletePaymentById(@PathVariable("id") id: Long): Mono<Long> {
        return paymentService.deletePaymentById(id)
    }

    @Put
    fun updatePayment(@Body paymentDto: PaymentDto): Mono<HttpResponse<PaymentDto>> {
        return paymentService.updatePayment(paymentDto)
            .map {
                HttpResponse.ok(it!!)
                    .headers { headers: MutableHttpHeaders ->
                        headers.location(location(it.id!!))
                    }
            }
    }

    private fun location(id: Long): URI {
        return UriBuilder.of("/groups").path(id.toString()).build()
    }
}
