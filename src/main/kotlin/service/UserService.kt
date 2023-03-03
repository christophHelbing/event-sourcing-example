package service

import event.EventStore
import port.UserWriteUseCase
import domain.write.UserAggregate
import domain.write.UserCommand

class UserService(
    private val eventStore: EventStore
) : UserWriteUseCase {
    override fun handleCommand(command: UserCommand) {
        eventStore.handleMutation(command.userId) { existingEventsForUser ->
            UserAggregate(existingEventsForUser).handleCommandToProduceNewEvents(command)
        }
    }
}