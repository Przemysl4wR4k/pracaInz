package com.example.inz20.extensions

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

val DATE_FORMAT = "dd.MM.yyyy HH:mm"

fun Long.convertToDate(dateFormat: String = DATE_FORMAT)  = DateTimeFormatter
    .ofPattern(dateFormat)
    .format(ZonedDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault()))