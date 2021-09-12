package com.jfontsaballs.krono.extensions

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.util.*

val ZoneId.fullDisplayName: String
    get() = getDisplayName(TextStyle.FULL, Locale.getDefault())

val ZoneId.formattedOffset: String
    get() = rules.getOffset(LocalDateTime.now()).toString()

val dateFormat: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
val ZonedDateTime.formattedDate : String
    get() = dateFormat.format(this)

val timeFormat: DateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)
val ZonedDateTime.formattedTime : String
    get() = timeFormat.format(this)
