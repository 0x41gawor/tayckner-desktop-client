package pl.gawor.taycknerdesktopclient.repository
interface ICrudRepository<Entity> {
    fun list(): List<Entity>?
    fun create(entity: Entity): Entity?
    fun read(id: Int): Entity?
    fun update(id: Int, entity: Entity): Entity?
    fun delete(id: Int): Boolean
}