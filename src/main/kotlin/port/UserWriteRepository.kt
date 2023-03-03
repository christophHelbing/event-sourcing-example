package port

import domain.write.UserWrite

interface UserWriteRepository {
    fun createUser(userWrite: UserWrite)

    fun updateUser(userWrite: UserWrite)
}