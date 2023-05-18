package com.example.expense.entities

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "expense")
class Expense(
    @NotBlank
    var name: String,
    @NotBlank
    var description: String,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    var userGroup: UserGroup? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
