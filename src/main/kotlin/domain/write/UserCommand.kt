package domain.write

sealed interface UserCommand {
    val userId: String

    data class CreateUserCommand(
        override val userId: String,
        val firstname: String,
        val lastname: String,
    ) : UserCommand

    data class UpdateUserCommand(
        override val userId: String,
        val firstname: String,
        val lastname: String,
        val contacts: Set<Contact>,
        val addresses: Set<Address>
    ) : UserCommand
}