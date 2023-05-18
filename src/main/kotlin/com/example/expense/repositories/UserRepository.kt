package com.example.expense.repositories

import com.example.expense.entities.User
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.reactive.ReactorPageableRepository

@Repository
interface UserRepository : ReactorPageableRepository<User, Long> {

}
