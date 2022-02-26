package pl.gawor.taycknerdesktopclient.repository.entity

import java.time.LocalDate
import java.time.LocalTime

data class ActivityEntity(
    val id: Int,
    val name: String,
    val startTime: LocalTime,
    val endTime: LocalTime?,
    val date: LocalDate,
    val duration: Int,
    val breaks: Int,
    val categoryId: Int
) {
    constructor() : this(0, "", LocalTime.now(), LocalTime.now(), LocalDate.now(), 0, 0, 1)
}
