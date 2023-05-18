package com.example.expense.services

import com.example.expense.entities.User
import com.example.expense.models.GroupDto
import com.example.expense.models.UserDto
import com.example.expense.repositories.GroupRepository
import com.example.expense.repositories.UserRepository
import jakarta.inject.Singleton
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Singleton
class UserService(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val commonService: CommonService
) {
    fun saveUser(userDto: UserDto): Mono<UserDto?> {
        val user = User(userDto.name)
        val group = groupRepository.findById(userDto.userGroup!!.id)
        user.userGroup = group.block()
        return userRepository.save(user).map { user -> commonService.toUserDto(user!!) }
    }

    fun getUserById(id: Long): Mono<UserDto?> {
        return userRepository.findById(id).map { user -> commonService.toUserDto(user) }
    }

    fun getAll(): Flux<UserDto> {
        return userRepository.findAll().map { user -> commonService.toUserDto(user) }
    }

    fun deleteUserById(id: Long): Mono<Long> {
        return userRepository.deleteById(id)
    }

    fun updateUserById(id: Long, name: String, userGroup: GroupDto?): Mono<UserDto?> {
        val user = User(name)
        user.id = id

        val group = groupRepository.findById(userGroup!!.id)
        user.userGroup = group.block()
        return userRepository.update(user).map { user -> commonService.toUserDto(user) }
    }
}
