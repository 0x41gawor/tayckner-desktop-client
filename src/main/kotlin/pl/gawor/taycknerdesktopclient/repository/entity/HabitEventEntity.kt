package pl.gawor.taycknerdesktopclient.repository.entity

import java.time.LocalDate

data class HabitEventEntity(
    val id: Int,
    val date: LocalDate,
    val comment: String,
    val count: Int,
    val habitId: Int
) {
    constructor() : this(0, LocalDate.now(), "", 1, 1)
}
