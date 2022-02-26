package pl.gawor.taycknerdesktopclient.repository

import pl.gawor.taycknerdesktopclient.repository.dbhelper.DbHelper
import pl.gawor.taycknerdesktopclient.repository.entity.CategoryEntity
import pl.gawor.taycknerdesktopclient.repository.entity.ScheduleEntity

class CategoryRepository : ICrudRepository<CategoryEntity> {

    private val dbHelper = DbHelper()

    override fun list(): List<CategoryEntity>? {
        println("CategoryRepository.list()")
        val query = "select * from category"

        val list = ArrayList<CategoryEntity>()
        val resultSet = dbHelper.executeResultQuery(query)

        if (resultSet != null) {
            var entity: CategoryEntity
            while (resultSet.next()) {
                entity = CategoryEntity(
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
        println("CategoryRepository.list() = null")
        return null
    }

    override fun create(entity: CategoryEntity): CategoryEntity? {
        println("CategoryRepository.create(entity = $entity)")
        val query = "insert into category value (0, '${entity.name}', '${entity.description}', '${entity.color}', ${entity.userId})"
        val id = dbHelper.executeInsertQuery(query)
        val result = read(id)
        println("CategoryRepository.create(entity = $entity) = $result")
        return null
    }

    override fun read(id: Int): CategoryEntity? {
        println("CategoryRepository.read(id = $id)")
        val query = "select * from category where id = $id"
        val resultSet = dbHelper.executeResultQuery(query)
        val result: CategoryEntity?
        if (resultSet != null && resultSet.next()) {
            val entity: CategoryEntity?
            entity = CategoryEntity(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("color"),
                resultSet.getInt("user_id")
            )
            result = entity
        } else result = null
        println("CategoryRepository.read(id = $id) = $result")
        return result
    }

    override fun update(id: Int, entity: CategoryEntity): CategoryEntity? {
        println("CategoryRepository.update(id = $id, entity = $entity)")
        val query = "update category set name = '${entity.name}', description = '${entity.description}', color = '${entity.color}', user_id = ${entity.userId} WHERE id = $id;"
        dbHelper.executeUpdateQuery(query)
        val result = read(id)
        println("ScheduleRepository.update(id = $id, entity = $entity) = $result")
        return result
    }

    override fun delete(id: Int): Boolean {
        println("CategoryRepository.delete(id = $id)")
        val query = "delete from category where id = $id"
        dbHelper.executeUpdateQuery(query)
        println("CategoryRepository.delete(id = $id) = true")
        return true
    }
}