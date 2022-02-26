package pl.gawor.taycknerdesktopclient.model

import java.time.LocalDate
import java.time.LocalTime

data class Activity(
    val id: Int,
    val name: String,
    val startTime: LocalTime,
    val endTime: LocalTime?,
    val date: LocalDate,
    val duration: Int,
    val breaks: Int,
    val category: Category
) {
    constructor() : this(0, "", LocalTime.now(), LocalTime.now(), LocalDate.now(), 0, 0, Category())
}
