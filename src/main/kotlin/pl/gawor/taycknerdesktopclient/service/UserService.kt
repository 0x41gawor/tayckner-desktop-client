package pl.gawor.taycknerdesktopclient.service

import pl.gawor.taycknerdesktopclient.model.User
import pl.gawor.taycknerdesktopclient.repository.UserRepository
import pl.gawor.taycknerdesktopclient.service.mapper.UserMapper

class UserService(private val repository: UserRepository) : ICrudService<User> {

    private val mapper = UserMapper()

    override fun list(): List<User>? {
        val entities = repository.list()
        val models  = ArrayList<User>()
        if (entities != null) {
            for (entity in entities) {
                val model = mapper.entityToModel(entity)
                models.add(model!!)
            }
            return models
        }
        return null
    }

    override fun create(model: User): User? {
        val entity = mapper.modelToEntity(model)
        val createdEntity = repository.create(entity)
        return mapper.entityToModel(createdEntity)
    }

    override fun read(id: Int): User? {
        val readEntity = repository.read(id)
        return mapper.entityToModel(readEntity)
    }

    override fun update(id: Int, model: User): User? {
        val entity = mapper.modelToEntity(model)
        val updatedEntity = repository.update(id, entity)
        return mapper.entityToModel(updatedEntity)
    }

    override fun delete(id: Int): Boolean {
        return repository.delete(id)
    }
}