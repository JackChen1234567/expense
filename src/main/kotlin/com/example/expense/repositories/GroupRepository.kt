package com.example.expense.repositories

import com.example.expense.entities.UserGroup
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.reactive.ReactorPageableRepository
import reactor.core.publisher.Mono
import javax.validation.constraints.NotBlank

@Repository
abstract class GroupRepository : ReactorPageableRepository<UserGroup, Long> {

    fun save(@NotBlank name: String): Mono<UserGroup?> {
        return save(UserGroup(name))
    }


    fun update(@Id id: Long, @NotBlank name: String): Mono<UserGroup> {
        val userGroup = UserGroup(name = name)
        userGroup.id = id
        return update(userGroup)
    }
}
