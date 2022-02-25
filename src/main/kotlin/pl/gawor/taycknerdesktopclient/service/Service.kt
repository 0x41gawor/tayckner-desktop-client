package pl.gawor.taycknerdesktopclient.service

import pl.gawor.taycknerdesktopclient.repository.ICrudRepository
import pl.gawor.taycknerdesktopclient.service.mapper.IMapper

open class Service<Model, Entity>(
    internal var repository: ICrudRepository<Entity>,
    internal val mapper: IMapper<Model, Entity>
) : ICrudService<Model> {

    override fun list(): List<Model>? {
        println("Service.list()")
        val entities = repository.list()
        val models = ArrayList<Model>()
        if (entities != null) {
            for (entity in entities) {
                val model = mapper.entityToModel(entity)
                models.add(model!!)
            }
            println("Service.list() = models")
            return models
        }
        println("Service.list() = null")
        return null
    }

    override fun create(model: Model): Model? {
        println("Service.create(model = $model)")
        val entity = mapper.modelToEntity(model)
        val createdEntity = repository.create(entity)
        val createdModel = mapper.entityToModel(createdEntity)
        println("Service.create(model = $model) = $createdModel")
        return createdModel
    }

    override fun read(id: Int): Model? {
        println("Service.read(id = $id)")
        val readEntity = repository.read(id)
        val readModel = mapper.entityToModel(readEntity)
        println("Service.read(id = $id) = $readModel")
        return readModel
    }

    override fun update(id: Int, model: Model): Model? {
        println("Service.update(id = $id, model = $model)")
        val entity = mapper.modelToEntity(model)
        val updatedEntity = repository.update(id, entity)
        val updateModel = mapper.entityToModel(updatedEntity)
        println("Service.update(id = $id, model = $model) = $updateModel")
        return updateModel
    }

    override fun delete(id: Int): Boolean {
        println("Service.delete(id = $id)")
        val result = repository.delete(id)
        println("Service.delete(id = $id) = $result")
        return result
    }
}