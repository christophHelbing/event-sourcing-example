package db

import common.mapToSet
import domain.read.Address
import domain.read.Contact
import domain.read.UserRead
import port.UserReadRepository

class InMemoryUserReadRepository(private val userTable: UserTable) : UserReadRepository {
    override fun getUserById(userId: String): UserRead {
        val user = userTable.users[userId]!!

        return UserRead(
            userId = user.userId,
            firstname = user.firstname,
            lastname = user.lastname,
            contacts = user.contacts.mapToSet {
                Contact(
                    type = it.type,
                    detail = it.detail
                )
            },
            addresses = user.addresses.mapToSet {
                Address(
                    street = it.street,
                    houseNr = it.houseNr,
                    zipCode = it.zipCode,
                    city = it.city
                )
            },
        )
    }

    override fun getAllUser(): List<UserRead> = userTable.users.values.map {
        UserRead(
            userId = it.userId,
            firstname = it.firstname,
            lastname = it.lastname,
            contacts = it.contacts.mapToSet { contact ->
                Contact(
                    type = contact.type,
                    detail = contact.detail
                )
            },
            addresses = it.addresses.mapToSet { address ->
                Address(
                    street = address.street,
                    houseNr = address.houseNr,
                    zipCode = address.zipCode,
                    city = address.city
                )
            },
        )
    }
}