package com.jfontsaballs.krono

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose
import com.jfontsaballs.krono.extensions.*
import java.time.Duration
import java.time.Instant
import java.time.ZoneId

data class ClockModel(
	val currentTimeZone: ZoneId = ZoneId.systemDefault(),
	val additionalTimeZones: List<ZoneId> = listOf(),
	val addTimeZone: AddTimeZoneModel?
) {
	private val now = Instant.now()
	val currentTime = now.atZone(currentTimeZone)
	val additionalTimes = additionalTimeZones.map { additionalTimeZones to now.atZone(it) }

	fun onTick() = copy(
		currentTimeZone = currentTimeZone,
		additionalTimeZones = additionalTimeZones
	)
}

data class AddTimeZoneModel(val searchText: String = "") {
	val zones = ZoneId.getAvailableZoneIds().map { ZoneId.of(it) }.let { zones ->
		if (searchText.isNotEmpty())
			zones.filter { it.fullDisplayName.contains(searchText) }
		else
			zones
	}
}

class ClockViewModel : ReduxModel<ClockModel>(ClockModel()) {
	init {
		schedule(Duration.ofSeconds(1000)) { it.onTick() }
	}
}

@Composable
fun ClockView() {
	val viewModel : ClockViewModel by viewModel()
	Column(Modifier.fillMaxWidth()) {
		TimeText(currentTime.formattedTime)
		TimeText(currentTime.formattedDate)
		TimeText(vm.currentTimeZone.fullDisplayName)
	}
}

@Composable
fun ColumnScope.TimeText(text: String) {
	Text(
		text,
		Modifier
			.align(CenterHorizontally)
			.padding(8.dp, 8.dp, 8.dp)
	)
}

@Preview
@Composable
fun ClockPreview() {
	Main(MainViewModel())
}