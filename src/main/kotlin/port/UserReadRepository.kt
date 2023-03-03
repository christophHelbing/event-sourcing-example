package port

import domain.read.UserRead

interface UserReadRepository {
    fun getUserById(userId: String): UserRead

    fun getAllUser(): List<UserRead>
}