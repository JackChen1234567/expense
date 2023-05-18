package com.example.expense.controllers

import com.example.expense.models.GroupDto
import com.example.expense.repositories.GroupRepository
import com.example.expense.services.GroupService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpHeaders
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI


@Controller("/groups")
open class GroupController(private val groupService: GroupService, private val groupRepository: GroupRepository) {
    @Get
    @Produces(MediaType.APPLICATION_JSON)
    fun getUserGroups(): Flux<GroupDto> {

        return groupService.getAll()
    }


    @Get("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUserGroupById(@PathVariable("id") id: Long): Mono<GroupDto?> {
        return groupService.getGroupById(id)
    }

    @Post
    fun saveUserGroup(@Body groupDto: GroupDto): Mono<HttpResponse<GroupDto>> {
        return groupService.saveGroup(groupDto)
            .map {
                HttpResponse.created(it!!)
                    .headers { headers: MutableHttpHeaders ->
                        headers.location(location(it.id!!))
                    }
            }
    }

    @Delete("{id}")
    fun deleteUserGroupById(@PathVariable("id") id: Long): Mono<Long> {
        return groupService.deleteGroupById(id)
    }

    @Put
    fun updateUserGroup(@Body groupDto: GroupDto): Mono<HttpResponse<GroupDto>> {
        return groupService.updateGroupById(groupDto.id!!, groupDto.name)
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
