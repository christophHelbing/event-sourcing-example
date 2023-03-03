package listener

import domain.write.UserWrite
import event.Consumer
import event.Event
import port.UserWriteRepository

class UserEventListener(
    private val userWriteRepository: UserWriteRepository,
) : Consumer {
    override fun consume(event: Event) {
        when (event) {
            is Event.UserCreatedEvent -> userWriteRepository.createUser(
                userWrite = UserWrite(
                    userId = event.userId,
                    firstname = event.firstname,
                    lastname = event.lastname
                )
            )
            is Event.UserUpdatedEvent -> userWriteRepository.updateUser(
                userWrite = UserWrite(
                    userId = event.userId,
                    firstname = event.firstname,
                    lastname = event.lastname,
                    contacts = event.contacts,
                    addresses = event.addresses,
                )
            )
        }
    }
}