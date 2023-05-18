package com.example.expense.controllers

import com.example.expense.models.UserDto
import com.example.expense.services.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpHeaders
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI

@Controller("/users")
class UserController(private val userService: UserService) {

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    fun getUserGroups(): Flux<UserDto> {
        return userService.getAll()
    }

    @Get("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUserById(@PathVariable("id") id: Long): Mono<UserDto?> {

        return userService.getUserById(id)
    }

    @Post
    fun saveUser(@Body userDto: UserDto): Mono<HttpResponse<UserDto>> {

        return userService.saveUser(userDto)
            .map {
                HttpResponse.created(it!!)
                    .headers { headers: MutableHttpHeaders ->
                        headers.location(location(it.id!!))
                    }
            }
    }

    @Delete("{id}")
    fun deleteUserById(@PathVariable("id") id: Long): Mono<Long> {
        return userService.deleteUserById(id)
    }

    @Put
    fun updateUser(@Body userDto: UserDto): Mono<HttpResponse<UserDto>> {

        return userService.updateUserById(userDto.id!!, userDto.name, userDto.userGroup)
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
