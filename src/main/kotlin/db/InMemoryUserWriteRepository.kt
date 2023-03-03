package db

import domain.write.UserWrite
import port.UserWriteRepository

class InMemoryUserWriteRepository(private val userTable: UserTable) : UserWriteRepository {
    override fun createUser(userWrite: UserWrite) {
        userTable.users[userWrite.userId] = userWrite
    }

    override fun updateUser(userWrite: UserWrite) {
        userTable.users[userWrite.userId] = userWrite
    }
}