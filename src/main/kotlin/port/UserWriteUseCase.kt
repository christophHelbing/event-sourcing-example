package port

import domain.write.UserCommand

interface UserWriteUseCase {
    fun handleCommand(command: UserCommand)
}