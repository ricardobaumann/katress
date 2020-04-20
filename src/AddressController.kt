package com.github.ricardobaumann

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

@Open
class AddressController {

    init {
        val config = HikariConfig("/hikari.properties")
        config.schema = "public"
        val ds = HikariDataSource(config)
        Database.connect(ds)
    }

    fun getAll(): List<Address> = transaction {
        Addresses.selectAll().map {
            Address(
                id = it[Addresses.id],
                firstName = it[Addresses.firstName],
                lastName = it[Addresses.lastName],
                street = it[Addresses.street],
                country = it[Addresses.country],
                zip = it[Addresses.zip]
            )
        }
    }

    fun insert(addressCreateCommand: AddressCreateCommand): Address =
        Address(//of course it should be moved to a service class :)
            id = UUID.randomUUID().toString(),
            firstName = addressCreateCommand.firstName,
            lastName = addressCreateCommand.lastName,
            street = addressCreateCommand.street,
            country = addressCreateCommand.country,
            zip = addressCreateCommand.zip
        )  .also {address ->
            transaction {
                Addresses.insert {
                    it[Addresses.id] = address.id
                    it[Addresses.firstName] = address.firstName
                    it[Addresses.lastName] = address.lastName
                    it[Addresses.street] = address.street
                    it[Addresses.country] = address.country
                    it[Addresses.zip] = address.zip
                }
            }
        }

    fun delete(id: String) {
        transaction {
            Addresses.deleteWhere { Addresses.id eq id }
        }
    }
}