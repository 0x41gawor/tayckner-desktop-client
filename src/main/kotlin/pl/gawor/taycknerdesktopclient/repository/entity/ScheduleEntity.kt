package pl.gawor.taycknerdesktopclient.repository.entity

import java.time.LocalDate
import java.time.LocalTime

data class ScheduleEntity(
    val id: Int,
    val name: String,
    val startTime: LocalTime?,
    val endTime: LocalTime?,
    val date: LocalDate,
    val duration: Double?,
    val userId: Int
) {
    constructor() : this(0, "", LocalTime.MIN, LocalTime.MIN, LocalDate.now(), 0.0, 1)
}
