package com.example.a2023_q2_mironov.data.converter

import com.example.a2023_q2_mironov.data.network.model.UserDto
import com.example.a2023_q2_mironov.domain.entity.User
import javax.inject.Inject

class UserConverter @Inject constructor() {

    fun convert(from: User): UserDto =
        UserDto(name = from.name, role = from.role)

    fun revert(from: UserDto): User =
        User(name = from.name, role = from.role)
}