package com.example.expense.controllers

import com.example.expense.models.ExpenseDto
import com.example.expense.services.ExpenseService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpHeaders
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI

@Controller("/expenses")
class ExpenseController(private val expenseService: ExpenseService) {
    @Get
    @Produces(MediaType.APPLICATION_JSON)
    fun getExpenses(): Flux<ExpenseDto> {
        return expenseService.getAll()
    }


    @Get("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getExpenseById(@PathVariable("id") id: Long): Mono<ExpenseDto?> {

        return expenseService.getExpenseById(id)
    }

    @Post
    fun saveExpense(@Body expenseDto: ExpenseDto): Mono<HttpResponse<ExpenseDto>> {

        return expenseService.saveExpense(expenseDto)
            .map {
                HttpResponse.created(it!!)
                    .headers { headers: MutableHttpHeaders ->
                        headers.location(location(it.id!!))
                    }
            }
    }

    @Delete("{id}")
    fun deleteExpenseById(@PathVariable("id") id: Long): Mono<Long> {
        return expenseService.deleteExpenseById(id)
    }

    @Put
    fun updateUserGroup(@Body expenseDto: ExpenseDto): Mono<HttpResponse<ExpenseDto>> {

        return expenseService.updateExpense(expenseDto)
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
