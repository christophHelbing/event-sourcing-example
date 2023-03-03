package domain.write

import event.Event

class UserAggregate(
    existingEvents: List<Event>
) {
    init {
        require(existingEvents.isEmpty() || existingEvents.first() is Event.UserCreatedEvent)
    }


    fun handleCommandToProduceNewEvents(command: UserCommand): List<Event> =
        command.let(
            ::handleCommand
        )

    private fun handleCommand(command: UserCommand) =
        when (command) {
            is UserCommand.CreateUserCommand -> addUser(command)
            is UserCommand.UpdateUserCommand -> updateUser(command)
        }

    private fun addUser(command: UserCommand.CreateUserCommand) = listOf(
        Event.UserCreatedEvent(
            userId = command.userId,
            firstname = command.firstname,
            lastname = command.lastname,
        )
    )

    private fun updateUser(command: UserCommand.UpdateUserCommand) = listOf(
        Event.UserUpdatedEvent(
            userId = command.userId,
            firstname = command.firstname,
            lastname = command.lastname,
            contacts = command.contacts,
            addresses = command.addresses,
        )
    )
}