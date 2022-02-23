package pl.gawor.taycknerdesktopclient.service.mapper

interface IMapper<Model, Entity> {
    fun modelToEntity(model: Model?): Entity
    fun entityToModel(entity: Entity?): Model?
}