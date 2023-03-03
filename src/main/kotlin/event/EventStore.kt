package event

interface EventStore {
    // Only for the test for checking the length of the event list
    val events: MutableList<Event>
    fun handleMutation(aggregateId: String, produceNewEvents: (List<Event>) -> List<Event>)

    fun eventStreamFor(aggregateId: String): List<Event>
}

class InMemoryEventStore(private val eventBus: EventBus) : EventStore {
    // Not private for checking the length of the event list
    override val events = mutableListOf<Event>()
    override fun handleMutation(aggregateId: String, produceNewEvents: (List<Event>) -> List<Event>) {
        val existingEvents = eventStreamFor(aggregateId)
        val newEvents = produceNewEvents(existingEvents)
        events.addAll(newEvents)
        newEvents.forEach { eventBus.sendEvent(it) }
    }

    override fun eventStreamFor(aggregateId: String): List<Event> =
        events.filter { it.userId == aggregateId }
}