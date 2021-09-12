package com.jfontsaballs.krono

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jfontsaballs.krono.ui.theme.KronoTheme

enum class Tab { Clock, Stopwatch }

@Composable
fun Main() {
	KronoTheme {
		var currentTab by remember { mutableStateOf(Tab.Clock) }

		Scaffold(
			bottomBar = { BottomAppBar(currentTab) { currentTab = it } },
			content = {
				when (currentTab) {
					Tab.Clock -> ClockView()
					Tab.Stopwatch -> StopwatchView()
				}
			}
		)
	}
}

@Composable
fun BottomAppBar(currentTab: Tab, setTab: (Tab) -> Unit) {
	BottomNavigation {
		AppNavigationItem(
			Tab.Clock, currentTab, setTab,
			R.drawable.ic_round_access_time_24,
			R.string.clock_tab_header
		)

		AppNavigationItem(
			Tab.Stopwatch, currentTab, setTab,
			R.drawable.ic_round_timer_24,
			R.string.stopwatch_tab_header
		)
	}
}

@Composable
fun RowScope.AppNavigationItem(
	thisTab: Tab, currentTab: Tab, setTab: (Tab) -> Unit,
	iconId: Int, captionId: Int
) {
	BottomNavigationItem(
		selected = thisTab === currentTab,
		onClick = { setTab(thisTab) },
		label = { Text(stringResource(id = captionId)) },
		alwaysShowLabel = false,
		icon = {
			Icon(
				painterResource(id = iconId),
				stringResource(id = captionId)
			)
		})
}

@Composable
@Preview(showBackground = true)
fun MainPreview() {
	Main()
}