package pl.gawor.taycknerdesktopclient.repository.entity


data class HabitEntity(
    val id: Int,
    val name: String,
    val description: String,
    val color: String,
    val userId: Int
) {
    constructor() : this(1, "", "", "", 1)
}
