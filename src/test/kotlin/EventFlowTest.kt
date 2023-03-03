import db.InMemoryUserReadRepository
import db.InMemoryUserWriteRepository
import db.UserTable
import domain.write.Address
import domain.write.Contact
import domain.write.UserCommand
import event.*
import listener.UserEventListener
import org.junit.jupiter.api.*
import port.UserReadRepository
import port.UserWriteRepository
import service.UserService
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class EventFlowTest {

    private lateinit var userTable: UserTable
    private lateinit var eventBus: EventBus
    private lateinit var eventStore: EventStore
    private lateinit var userReadRepository: UserReadRepository
    private lateinit var userWriteRepository: UserWriteRepository
    private lateinit var userService: UserService
    private lateinit var userEventListener: Consumer

    private lateinit var userId: String
    private lateinit var additionalUserId: String

    @BeforeAll
    fun setup() {
        userId = UUID.randomUUID().toString()
        additionalUserId = UUID.randomUUID().toString()

        userTable = UserTable()
        eventBus = MockedEventbus()
        eventStore = InMemoryEventStore(eventBus = eventBus)
        userReadRepository = InMemoryUserReadRepository(userTable = userTable)
        userWriteRepository = InMemoryUserWriteRepository(userTable = userTable)

        userService = UserService(eventStore = eventStore)
        userEventListener = UserEventListener(userWriteRepository = userWriteRepository)
        eventBus.registerConsumer(userEventListener)
    }

    @Test
    @Order(1)
    fun `adding a user`() {
        userService.handleCommand(
            UserCommand.CreateUserCommand(
                userId = userId,
                firstname = "Christoph",
                lastname = "Helbing",
            )
        )


        val user = userReadRepository.getUserById(userId = userId)
        assertEquals("Christoph", user.firstname)
        assertEquals("Helbing", user.lastname)
        assertTrue { user.contacts.isEmpty() }
        assertTrue { user.addresses.isEmpty() }
        assertTrue { eventStore.events.size == 1 }
    }

    @Test
    @Order(2)
    fun `updating the user`() {
        userService.handleCommand(
            UserCommand.UpdateUserCommand(
                userId = userId,
                firstname = "Christian",
                lastname = "Helbing",
                contacts = setOf(
                    Contact(type = "friend", detail = "detail info")
                ),
                addresses = setOf(
                    Address(
                        street = "Musterstr.",
                        houseNr = "12",
                        zipCode = "08151",
                        city = "Musterstadt",
                    )
                )
            )
        )


        val user = userReadRepository.getUserById(userId = userId)
        assertEquals("Christian", user.firstname)
        assertEquals("Helbing", user.lastname)
        assertTrue { user.contacts.isNotEmpty() }
        assertTrue { user.addresses.isNotEmpty() }
        assertEquals(2, eventStore.events.size)
    }

    @Test
    @Order(3)
    fun `adding additional user`() {
        userService.handleCommand(
            UserCommand.CreateUserCommand(
                userId = additionalUserId,
                firstname = "Max",
                lastname = "Mustermann",
            )
        )


        val user = userReadRepository.getUserById(userId = additionalUserId)
        assertEquals("Max", user.firstname)
        assertEquals("Mustermann", user.lastname)
        assertTrue { user.contacts.isEmpty() }
        assertTrue { user.addresses.isEmpty() }
        assertEquals(3, eventStore.events.size)
        assertEquals(2, userReadRepository.getAllUser().size)
    }
}