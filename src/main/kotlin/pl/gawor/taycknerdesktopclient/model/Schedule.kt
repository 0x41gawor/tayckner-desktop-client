package pl.gawor.taycknerdesktopclient.model

import java.time.LocalDate
import java.time.LocalTime

data class Schedule(
    val id: Int,
    val name: String,
    val startTime: LocalTime?,
    val endTime: LocalTime?,
    val date: LocalDate,
    val duration: Double?,
    val user: User
) {
    constructor() : this(0, "", LocalTime.now(), LocalTime.now(), LocalDate.now(), 0.0, User())
}