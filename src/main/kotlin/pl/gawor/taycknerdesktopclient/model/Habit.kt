package pl.gawor.taycknerdesktopclient.model

data class Habit(
    val id: Int,
    val name: String,
    val description: String,
    val color: String,
    val user:User
) {
    constructor() : this(1, "", "", "", User())
}
