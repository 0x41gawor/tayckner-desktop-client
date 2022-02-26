package pl.gawor.taycknerdesktopclient.repository.entity

data class CategoryEntity(
    val id: Int,
    val name: String,
    val description: String,
    val color: String,
    val userId: Int
) {
    constructor() : this(0, "", "", "#ffffff", 1)
}
