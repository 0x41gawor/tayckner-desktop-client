package pl.gawor.taycknerdesktopclient.repository.entity

data class UserEntity(
    val id: Int,
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String
) {
    constructor() : this(0, "", "", "", "", "")
}
