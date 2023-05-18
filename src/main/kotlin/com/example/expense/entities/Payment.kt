package com.example.expense.entities

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "payment")
class Payment(
    @NotBlank
    var name: String,
    var amount: Double,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    var user: User? = null,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "expense_id")
    var expense: Expense? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0


}
