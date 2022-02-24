package pl.gawor.taycknerdesktopclient.service

import pl.gawor.taycknerdesktopclient.repository.ICrudRepository
import pl.gawor.taycknerdesktopclient.service.mapper.IMapper

open class Service<Model, Entity>(
    internal var repository: ICrudRepository<Entity>,
    internal val mapper: IMapper<Model, Entity>
) : ICrudService<Model> {

    override fun list(): List<Model>? {
        val entities = repository.list()
        val models = ArrayList<Model>()
        if (entities != null) {
            for (entity in entities) {
                val model = mapper.entityToModel(entity)
                models.add(model!!)
            }
            return models
        }
        return null
    }

    override fun create(model: Model): Model? {
        val entity = mapper.modelToEntity(model)
        val createdEntity = repository.create(entity)
        return mapper.entityToModel(createdEntity)
    }

    override fun read(id: Int): Model? {
        val readEntity = repository.read(id)
        return mapper.entityToModel(readEntity)
    }

    override fun update(id: Int, model: Model): Model? {
        val entity = mapper.modelToEntity(model)
        val updatedEntity = repository.update(id, entity)
        return mapper.entityToModel(updatedEntity)
    }

    override fun delete(id: Int): Boolean {
        return repository.delete(id)
    }
}