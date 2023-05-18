package com.example.expense.services

import com.example.expense.entities.UserGroup
import com.example.expense.models.GroupDto
import com.example.expense.repositories.GroupRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono

class GroupServiceTest {

    @MockK
    lateinit var groupRepository: GroupRepository

    @InjectMockKs
    lateinit var commonService: CommonService

    @InjectMockKs
    lateinit var groupService: GroupService

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testSaveGroup() {
        val expectGroupName = "tester"
        val expectGroupId = 1L

        val mockGroup = UserGroup(expectGroupName)
        mockGroup.id = expectGroupId

        val expectedGroupDto = GroupDto(expectGroupId, "Tester")

        every { groupRepository.save(any()) } returns Mono.just(mockGroup)

        val groupAfterSaved = groupService.saveGroup(expectedGroupDto).block()
        Assertions.assertEquals(expectGroupName, groupAfterSaved!!.name)
        Assertions.assertEquals(expectGroupId, groupAfterSaved.id)
    }

    @Test
    fun testGetGroup() {
        val expectGroupName = "tester"
        val expectGroupId = 1L

        val mockGroup = UserGroup(expectGroupName)
        mockGroup.id = expectGroupId

        every { groupRepository.findById(any()) } returns Mono.just(mockGroup)

        val groupAfterRetrieve = groupService.getGroupById(expectGroupId).block()
        Assertions.assertEquals(expectGroupName, groupAfterRetrieve!!.name)
        Assertions.assertEquals(expectGroupId, groupAfterRetrieve.id)
    }

    @Test
    fun testDeleteGroup() {
        val expectGroupId = 1L

        every { groupRepository.deleteById(any()) } returns Mono.just(1L)

        groupService.deleteGroupById(expectGroupId)
        verify { groupRepository.deleteById(expectGroupId) }
    }
}
