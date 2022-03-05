package pl.gawor.taycknerdesktopclient.repository

import pl.gawor.taycknerdesktopclient.repository.dbhelper.DbHelper
import pl.gawor.taycknerdesktopclient.repository.entity.HabitEntity

class HabitRepository : ICrudRepository<HabitEntity> {

    private val dbHelper = DbHelper()

    override fun list(): List<HabitEntity>? {
        println("HabitRepository.list()")
        val query = "select * from habit"

        val list = ArrayList<HabitEntity>()
        val resultSet = dbHelper.executeResultQuery(query)

        if (resultSet != null) {
            var entity: HabitEntity
            while (resultSet.next()) {
                entity = HabitEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("color"),
                    resultSet.getInt("user_id")
                )
                list.add(entity)
            }
            println("CategoryRepository.list() = $list")
            return list
        }
        println("HabitRepository.list() = null")
        return null
    }

    override fun create(entity: HabitEntity): HabitEntity? {
        println("HabitRepository.create(entity = $entity)")
        val query = "insert into habit value (0, '${entity.name}', '${entity.description}', '${entity.color}', ${entity.userId})"
        val id = dbHelper.executeInsertQuery(query)
        val result = read(id)
        println("HabitRepository.create(entity = $entity) = $result")
        return result
    }

    override fun read(id: Int): HabitEntity? {
        println("HabitRepository.read(id = $id)")
        val query = "select * from habit where id = $id"
        val resultSet = dbHelper.executeResultQuery(query)
        val result: HabitEntity?
        if (resultSet != null && resultSet.next()) {
            val entity: HabitEntity?
            entity = HabitEntity(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("color"),
                resultSet.getInt("user_id")
            )
            result = entity
        } else result = null
        println("HabitRepository.read(id = $id) = $result")
        return result
    }

    override fun update(id: Int, entity: HabitEntity): HabitEntity? {
        println("HabitRepository.update(id = $id, entity = $entity)")
        val query = "update habit set name = '${entity.name}', description = '${entity.description}', color = '${entity.color}', user_id = ${entity.userId} WHERE id = $id;"
        dbHelper.executeUpdateQuery(query)
        val result = read(id)
        println("HabitRepository.update(id = $id, entity = $entity) = $result")
        return result
    }

    override fun delete(id: Int): Boolean {
        println("HabitRepository.delete(id = $id)")
        val query = "delete from habit where id = $id"
        dbHelper.executeUpdateQuery(query)
        println("HabitRepository.delete(id = $id) = true")
        return true
    }
}