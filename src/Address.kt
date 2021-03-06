package com.github.ricardobaumann

@NoArg
data class Address (
    val id: String,
    val firstName: String,
    val lastName: String,
    val street: String,
    val country: String,
    val zip: String
)