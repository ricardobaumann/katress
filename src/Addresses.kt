package com.github.ricardobaumann

import org.jetbrains.exposed.sql.Table

object Addresses: Table() {
    val id = text("id").primaryKey()
    val firstName = text("first_name")
    val lastName = text("last_name")
    val street = text("street")
    val country = text("country")
    val zip = text("zip")
}