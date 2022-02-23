package pl.gawor.taycknerdesktopclient.repository

import pl.gawor.taycknerdesktopclient.repository.entity.User

interface ICrudRepository<Entity> {
    fun list(): List<Entity>?
    fun create(entity: Entity): User?
    fun read(id: Int): User?
    fun update(id: Int, entity: Entity): User?
    fun delete(id: Int): Boolean
}