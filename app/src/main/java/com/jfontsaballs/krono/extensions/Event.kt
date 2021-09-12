package com.jfontsaballs.krono.extensions

class Event {
	private val subscriptions = mutableListOf<() -> Unit>()

	fun subscribe(subscription: () -> Unit): () -> Unit {
		subscriptions.add(subscription)
		return { subscriptions.remove(subscription) }
	}

	operator fun invoke() {
		subscriptions.forEach {
			it()
		}
	}

	operator fun plusAssign(subscription: () -> Unit): Unit {
		subscribe(subscription)
	}
}