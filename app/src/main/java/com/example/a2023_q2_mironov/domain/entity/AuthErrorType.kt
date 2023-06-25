package com.example.a2023_q2_mironov.domain.entity

enum class AuthErrorType {
    WRONG_LOGIN_OR_PASSWORD,
    USER_EXIST,
    UNKNOWN,
    CONNECTION;
}