package com.jfontsaballs.krono

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import java.time.Duration
import java.time.Instant

class StopwatchViewModel () {
	val startTime = MutableStateFlow<Instant?>(null)
	val elapsedTime = flow {
		while (true) {
			val startTime = startTime.value
			if (startTime != null)
				emit(Duration.between(startTime, Instant.now()))
			else
				emit(Duration.ZERO)
		}
	}
}

@Composable
fun StopwatchView() {

}