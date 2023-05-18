package com.example.expense.models

import io.micronaut.serde.annotation.Serdeable
import javax.validation.constraints.NotBlank


@Serdeable
class UserDto(val id: Long?, @NotBlank var name: String) {
    var userGroup: GroupDto? = null
}
