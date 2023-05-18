package com.example.expense.services

import com.example.expense.entities.User
import com.example.expense.entities.UserGroup
import com.example.expense.models.GroupDto
import com.example.expense.models.UserDto
import com.example.expense.repositories.GroupRepository
import com.example.expense.repositories.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono


class UserServiceTest {
    @MockK
    lateinit var userRepository: UserRepository

    @MockK
    lateinit var groupRepository: GroupRepository

    @InjectMockKs
    lateinit var commonService: CommonService

    @InjectMockKs
    lateinit var userService: UserService

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testSaveUser() {
        val expectUserName = "user"
        val expectGroupName = "tester"
        val expectGroupId = 1L
        val expectUserId = 1L

        val mockGroup = UserGroup(expectGroupName)
        mockGroup.id = expectGroupId
        val mockUser = User(expectUserName)
        mockUser.id = expectUserId
        val mockUserWithGroup = User(expectUserName)
        mockUserWithGroup.id = expectGroupId
        mockUserWithGroup.userGroup = mockGroup
        val expectedDto = UserDto(1, expectUserName)
        expectedDto.userGroup = GroupDto(expectGroupId, "Tester")

        every { groupRepository.findById(any()).block() } returns mockGroup
        every { userRepository.findById(any()).block() } returns mockUser
        every { userRepository.save(any()) } returns Mono.just(mockUserWithGroup)

        val userAfterSaved = userService.saveUser(expectedDto).block()
        assertEquals(expectUserName, userAfterSaved?.name)
        assertEquals(expectUserId, userAfterSaved?.id)
        assertEquals(expectGroupName, userAfterSaved!!.userGroup?.name)
        assertEquals(expectGroupId, userAfterSaved.userGroup?.id)
    }


    @Test
    fun testGetUser() {
        val expectUserName = "user"
        val expectGroupName = "tester"
        val expectGroupId = 1L
        val expectUserId = 1L

        val mockGroup = UserGroup(expectGroupName)
        mockGroup.id = expectGroupId
        val mockUser = User(expectUserName)
        mockUser.id = expectUserId
        val mockUserWithGroup = User(expectUserName)
        mockUserWithGroup.id = expectGroupId
        mockUserWithGroup.userGroup = mockGroup
        val expectedDto = UserDto(expectUserId, expectUserName)
        expectedDto.userGroup = GroupDto(expectGroupId, "Tester")

        every { userRepository.findById(any()) } returns Mono.just(mockUserWithGroup)

        val userAfterRetrieve = userService.getUserById(expectUserId).block()
        assertEquals(expectUserName, userAfterRetrieve?.name)
        assertEquals(expectUserId, userAfterRetrieve?.id)
        assertEquals(expectGroupName, userAfterRetrieve!!.userGroup?.name)
        assertEquals(expectGroupId, userAfterRetrieve.userGroup?.id)
    }

    @Test
    fun testDeleteUser() {
        val expectUserId = 1L
        every { userRepository.deleteById(any()) } returns Mono.just(1L)
        userService.deleteUserById(expectUserId)
        verify { userRepository.deleteById(expectUserId) }
    }

}
