package com.example.expense.entities

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "users_group")
class UserGroup(
    @NotBlank
    var name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER, mappedBy = "userGroup")
    private val userList = mutableListOf<User>()

    val users get() = userList.toList()

    fun addUser(user: User) {
        userList += user
    }
}
