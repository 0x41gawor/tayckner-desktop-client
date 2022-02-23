package pl.gawor.taycknerdesktopclient.repository.entity

import java.time.LocalDateTime

data class Schedule(
    val id: Int,
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val duration: Double,
    val userId: Int
) {
    constructor() : this(0, "", LocalDateTime.MIN, LocalDateTime.MIN, 0.0, 0)
}
