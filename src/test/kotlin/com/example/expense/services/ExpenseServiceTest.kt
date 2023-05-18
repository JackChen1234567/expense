package com.example.expense.services

import com.example.expense.entities.Expense
import com.example.expense.entities.UserGroup
import com.example.expense.models.ExpenseDto
import com.example.expense.models.GroupDto
import com.example.expense.repositories.ExpenseRepository
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

class ExpenseServiceTest {
    @MockK
    lateinit var groupRepository: GroupRepository

    @MockK
    lateinit var expenseRepository: ExpenseRepository

    @InjectMockKs
    lateinit var commonService: CommonService

    @InjectMockKs
    lateinit var expenseService: ExpenseService

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testSaveExpense() {
        val expectGroupName = "tester"
        val expectGroupId = 1L
        val expectExpenseId = 2L
        val expectExpenseName = "pc expense"
        val expectExpenseDesc = "expense for buying pc"
        val expectExpense = Expense(expectExpenseName, expectExpenseDesc)
        expectExpense.id = expectExpenseId

        val mockGroup = UserGroup(expectGroupName)
        mockGroup.id = expectGroupId
        expectExpense.userGroup = mockGroup

        val mockGroupDto = GroupDto(expectGroupId, expectGroupName)

        val mockExpense = ExpenseDto(expectExpenseId, expectExpenseName, expectExpenseDesc)
        mockExpense.group = mockGroupDto


        every { groupRepository.findById(any()).block() } returns mockGroup
        every { expenseRepository.save(any()) } returns Mono.just(expectExpense)

        val expenseAfterSaved = expenseService.saveExpense(mockExpense).block()
        Assertions.assertEquals(expectExpenseName, expenseAfterSaved!!.name)
        Assertions.assertEquals(expectExpenseId, expenseAfterSaved.id)
        Assertions.assertEquals(expectExpenseDesc, expenseAfterSaved.description)
        Assertions.assertEquals(expectGroupId, expenseAfterSaved.group!!.id)
        Assertions.assertEquals(expectGroupName, expenseAfterSaved.group!!.name)
    }

    @Test
    fun testGetExpense() {
        val expectGroupName = "tester"
        val expectGroupId = 1L
        val expectExpenseId = 2L
        val expectExpenseName = "pc expense"
        val expectExpenseDesc = "expense for buying pc"
        val expectExpense = Expense(expectExpenseName, expectExpenseDesc)
        expectExpense.id = expectExpenseId

        val mockGroup = UserGroup(expectGroupName)
        mockGroup.id = expectGroupId
        expectExpense.userGroup = mockGroup

        val mockGroupDto = GroupDto(expectGroupId, expectGroupName)
        val mockExpense = ExpenseDto(expectExpenseId, expectExpenseName, expectExpenseDesc)
        mockExpense.group = mockGroupDto

        every { expenseRepository.findById(expectExpenseId) } returns Mono.just(expectExpense)

        val expenseAfterRetrieve = expenseService.getExpenseById(expectExpenseId).block()

        Assertions.assertEquals(expectExpenseName, expenseAfterRetrieve!!.name)
        Assertions.assertEquals(expectExpenseId, expenseAfterRetrieve.id)
        Assertions.assertEquals(expectExpenseDesc, expenseAfterRetrieve.description)
        Assertions.assertEquals(expectGroupId, expenseAfterRetrieve.group!!.id)
        Assertions.assertEquals(expectGroupName, expenseAfterRetrieve.group!!.name)
    }

    @Test
    fun testDeleteExpense() {
        val expectExpenseId = 1L
        every { expenseService.deleteExpenseById(any()) } returns Mono.just(1L)
        expenseService.deleteExpenseById(expectExpenseId)
        verify { expenseService.deleteExpenseById(expectExpenseId) }
    }
}
