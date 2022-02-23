package pl.gawor.taycknerdesktopclient.model

data class User(
    val id: Int,
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String
) {
    constructor() : this(0, "", "", "", "", "")
}
