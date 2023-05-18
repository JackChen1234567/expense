package com.example.expense.entities

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "users")
class User(
    @NotBlank
    var name: String,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    var userGroup: UserGroup? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
