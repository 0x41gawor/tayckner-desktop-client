package pl.gawor.taycknerdesktopclient.model

import java.time.LocalDateTime

data class Schedule(
    val id: Int,
    val name: String,
    val startTime: LocalDateTime?,
    val endTime: LocalDateTime?,
    val duration: Double?,
    val user: User
) {
    constructor() : this(0, "", LocalDateTime.MIN, LocalDateTime.MIN, 0.0, User())
}