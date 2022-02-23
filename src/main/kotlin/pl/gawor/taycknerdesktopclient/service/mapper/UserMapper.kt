package pl.gawor.taycknerdesktopclient.service.mapper

import pl.gawor.taycknerdesktopclient.model.User
import pl.gawor.taycknerdesktopclient.repository.entity.UserEntity

class UserMapper : IMapper<User, UserEntity> {

    override fun modelToEntity(model: User?): UserEntity {
        if (model == null) return UserEntity()
        return UserEntity(model.id, model.username, model.password, model.firstName, model.lastName, model.email)
    }

    override fun entityToModel(entity: UserEntity?): User? {
        if (entity == null) return null
        return User(entity.id, entity.username, entity.password, entity.firstName, entity.lastName, entity.email)
    }
}