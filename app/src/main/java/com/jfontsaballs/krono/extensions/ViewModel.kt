package com.jfontsaballs.krono.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

fun <T> ViewModel.stateFlow(
	initialValue: T,
	nextValues: suspend FlowCollector<T>.() -> Unit
): StateFlow<T> {
	val stateFlow = MutableStateFlow(initialValue)
	val collector = object : FlowCollector<T> {
		override suspend fun emit(value: T) {
			stateFlow.value = value
		}
	}
	viewModelScope.launch {
		collector.nextValues()
	}
	return stateFlow
}

open class ViewModelBase : ViewModel() {
	fun <T, R> StateFlow<T>.stateMap(transform: (T) -> R): StateFlow<R> =
		this
			.map { transform(it) }
			.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), transform(value))
}
