package db

import domain.write.UserWrite

class UserTable {
    var users: MutableMap<String, UserWrite> = mutableMapOf()
}