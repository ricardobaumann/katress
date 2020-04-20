package com.github.ricardobaumann

data class AddressCreateCommand (
    val firstName: String,
    val lastName: String,
    val street: String,
    val country: String,
    val zip: String
)