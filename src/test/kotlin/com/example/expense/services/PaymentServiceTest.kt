package com.example.expense.services

import com.example.expense.entities.Expense
import com.example.expense.entities.Payment
import com.example.expense.entities.User
import com.example.expense.entities.UserGroup
import com.example.expense.models.ExpenseDto
import com.example.expense.models.PaymentDto
import com.example.expense.models.UserDto
import com.example.expense.repositories.ExpenseRepository
import com.example.expense.repositories.PaymentRepository
import com.example.expense.repositories.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono

class PaymentServiceTest {
    @MockK
    lateinit var userRepository: UserRepository

    @MockK
    lateinit var expenseRepository: ExpenseRepository

    @MockK
    lateinit var paymentRepository: PaymentRepository

    @InjectMockKs
    lateinit var commonService: CommonService

    @InjectMockKs
    lateinit var paymentService: PaymentService

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testSavePayment() {
        val expectUserName = "John don"
        val expectUserId = 1L
        val expectExpenseId = 2L
        val expectExpenseName = "pc expense"
        val expectExpenseDesc = "expense for buying pc"
        val expectPaymentName = "Payment to buy pc"
        val expectPaymentId = 1L
        val expectPaymentAmount = 200.55
        val expectGroupName = "tester"
        val expectGroupId = 1L

        val mockGroup = UserGroup(expectGroupName)
        mockGroup.id = expectGroupId
        val expectExpense = Expense(expectExpenseName, expectExpenseDesc)
        expectExpense.id = expectExpenseId
        expectExpense.userGroup = mockGroup
        val expectUserDto = UserDto(expectUserId, expectUserName)
        val expectExpenseDto = ExpenseDto(expectExpenseId, expectExpenseName, expectExpenseDesc)

        val mockUser = User(expectUserName)
        mockUser.id = expectUserId
        val mockExpense = Expense(expectExpenseName, expectExpenseDesc)
        mockExpense.id = expectExpenseId
        mockExpense.userGroup = mockGroup
        val mockPayment = Payment(expectPaymentName, expectPaymentAmount, mockUser, mockExpense)
        mockPayment.id = expectPaymentId

        val paymentDto =
            PaymentDto(expectPaymentId, expectPaymentName, expectPaymentAmount, expectUserDto, expectExpenseDto)

        every { userRepository.findById(any()).block() } returns mockUser
        every { expenseRepository.findById(any()).block() } returns mockExpense
        every { paymentRepository.save(any()) } returns Mono.just(mockPayment)

        val paymentAfterSaved = paymentService.savePayment(paymentDto).block()
        Assertions.assertEquals(expectPaymentName, paymentAfterSaved!!.name)
        Assertions.assertEquals(expectPaymentId, paymentAfterSaved.id)
        Assertions.assertEquals(expectPaymentAmount, paymentAfterSaved.amount)
        Assertions.assertEquals(expectUserName, paymentAfterSaved.user.name)
        Assertions.assertEquals(expectUserId, paymentAfterSaved.user.id)
        Assertions.assertEquals(expectExpenseName, paymentAfterSaved.expense.name)
        Assertions.assertEquals(expectExpenseId, paymentAfterSaved.expense.id)
        Assertions.assertEquals(expectExpenseDesc, paymentAfterSaved.expense.description)
    }

    @Test
    fun testDeletePayment() {
        val expectExpenseId = 1L
        every { paymentService.deletePaymentById(expectExpenseId) } returns Mono.just(1L)
        paymentService.deletePaymentById(expectExpenseId)
        verify { paymentService.deletePaymentById(expectExpenseId) }
    }
}
