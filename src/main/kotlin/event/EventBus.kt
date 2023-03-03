package event

interface Consumer {
    fun consume(event: Event)
}

interface EventBus {
    fun registerConsumer(consumer: Consumer)
    fun sendEvent(event: Event)
}

class MockedEventbus : EventBus {
    val consumers = mutableListOf<Consumer>()
    override fun registerConsumer(consumer: Consumer) {
        consumers.add(element = consumer)
    }

    override fun sendEvent(event: Event) {
        println("Sending event $event")
        consumers.forEach {
            it.consume(event = event)
        }
    }
}