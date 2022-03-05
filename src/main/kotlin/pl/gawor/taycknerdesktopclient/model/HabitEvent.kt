package pl.gawor.taycknerdesktopclient.model

import java.time.LocalDate

data class HabitEvent(
    val id: Int,
    val date: LocalDate,
    val comment: String,
    val count: Int,
    val habit: Habit
) {
    constructor() : this(0, LocalDate.now(), "", 1, Habit())
}
