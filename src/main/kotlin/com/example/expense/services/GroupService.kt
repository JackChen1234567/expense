package com.example.expense.services

import com.example.expense.models.GroupDto
import com.example.expense.repositories.GroupRepository
import jakarta.inject.Singleton
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Singleton
class GroupService(private val groupRepository: GroupRepository, private val commonService: CommonService) {

    fun getGroupById(groupId: Long): Mono<GroupDto?> {
        return groupRepository.findById(groupId).map { group -> commonService.toGroupDto(group) }
    }

    fun saveGroup(groupDto: GroupDto): Mono<GroupDto?> {
        return groupRepository.save(groupDto.name).map { group -> commonService.toGroupDto(group!!) }
    }

    fun getAll(): Flux<GroupDto> {
        return groupRepository.findAll().map { group -> commonService.toGroupDto(group) }
    }

    fun updateGroupById(groupId: Long, name: String): Mono<GroupDto?> {
        return groupRepository.update(groupId, name).map { group -> commonService.toGroupDto(group) }
    }

    fun deleteGroupById(id: Long): Mono<Long> {
        return groupRepository.deleteById(id)
    }

}
