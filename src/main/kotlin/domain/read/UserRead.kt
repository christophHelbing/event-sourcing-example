package domain.read

data class UserRead (
    val userId: String,
    val firstname: String,
    val lastname: String,
    val contacts: Set<Contact> = emptySet(),
    val addresses: Set<Address> = emptySet(),
)