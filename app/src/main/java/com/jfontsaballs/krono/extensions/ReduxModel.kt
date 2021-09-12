package com.jfontsaballs.krono.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.Duration

open class ReduxModel<T>(initialState: T) : ViewModel() {
	private val mutableState = MutableStateFlow(initialState)
	val state get() = mutableState

	fun update(updater: (T) -> T) {
		mutableState.value = updater(mutableState.value)
	}
}

fun <T> ReduxModel<T>.schedule(delay: Duration, updater: (T) -> T) {
	viewModelScope.launch {
		val delayMillis = delay.toMillis()
		while (true) {
			delay(delayMillis + 1 - System.currentTimeMillis() % delayMillis)
			update(updater)
		}
	}
}

@Composable
fun <T> ReduxModel<T>.collectAsState() = state.collectAsState()