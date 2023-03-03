package event

import domain.write.Address
import domain.write.Contact

sealed class Event {
    abstract val userId: String

    data class UserCreatedEvent(
        override val userId: String,
        val firstname: String,
        val lastname: String,
    ) : Event()

    data class UserUpdatedEvent(
        override val userId: String,
        val firstname: String,
        val lastname: String,
        val contacts: Set<Contact>,
        val addresses: Set<Address>,
    ) : Event()
}
