package dam.adri.domain.modelo.entities

import dam.adri.domain.modelo.dto.UserDto

data class User(
    val id: Int?,
    val name: String,
    val password: String
)

fun UserDto.toUser(): User {
    return User(
        id = this.id,
        name = this.name,
        password = this.password
    )
}