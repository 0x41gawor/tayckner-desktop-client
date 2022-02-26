package pl.gawor.taycknerdesktopclient.model

data class Category(
    val id: Int,
    val name: String,
    val description: String,
    val color: String,
    val user: User
) {
    constructor() : this(0, "", "", "#ffffff", User())
}
