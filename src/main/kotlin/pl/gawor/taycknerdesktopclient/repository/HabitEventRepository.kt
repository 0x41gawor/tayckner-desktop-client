package pl.gawor.taycknerdesktopclient.repository

import pl.gawor.taycknerdesktopclient.repository.dbhelper.DbHelper
import pl.gawor.taycknerdesktopclient.repository.entity.HabitEventEntity

class HabitEventRepository : ICrudRepository<HabitEventEntity> {

    private val dbHelper = DbHelper()


    override fun list(): List<HabitEventEntity>? {
        println("HabitEventRepository.list()")
        val query = "select * from habit_event"

        val list = ArrayList<HabitEventEntity>()
        val resultSet = dbHelper.executeResultQuery(query)

        if (resultSet != null) {
            var entity: HabitEventEntity
            while (resultSet.next()) {
                entity = HabitEventEntity(
                    resultSet.getInt("id"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getString("comment"),
                    resultSet.getInt("count"),
                    resultSet.getInt("habit_id")
                )
                list.add(entity)
            }
            println("HabitEventRepository.list() = $list")
            return list
        }
        println("HabitEventRepository.list() = null")
        return null
    }

    override fun create(entity: HabitEventEntity): HabitEventEntity? {
        println("HabitEventRepository.create(entity = $entity)")
        val query = "INSERT INTO habit_event  VALUE (0, ${entity.habitId}, '${entity.date}', '${entity.comment}', ${entity.count})"
        val id = dbHelper.executeInsertQuery(query)
        val result = read(id)
        println("HabitEventRepository.create(entity = $entity) = $result")
        return result
    }

    override fun read(id: Int): HabitEventEntity? {
        println("HabitEventRepository.read(id = $id)")
        val query = "select * from habit_event where id = $id"
        val resultSet = dbHelper.executeResultQuery(query)
        val result: HabitEventEntity?
        if (resultSet != null && resultSet.next()) {
            val entity: HabitEventEntity?
            entity = HabitEventEntity(
                resultSet.getInt("id"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getString("comment"),
                resultSet.getInt("count"),
                resultSet.getInt("habit_id")
            )
            result = entity
        } else result = null
        println("HabitEventRepository.read(id = $id) = $result")
        return result
    }

    override fun update(id: Int, entity: HabitEventEntity): HabitEventEntity? {
        println("HabitEventRepository.update(id = $id, entity = $entity)")
        val query = "UPDATE habit_event SET habit_id = ${entity.habitId}, date = '${entity.date}', comment = '${entity.comment}', count = ${entity.count} WHERE id = $id"
        dbHelper.executeUpdateQuery(query)
        val result = read(id)
        println("HabitEventRepository.update(id = $id, entity = $entity) = $result")
        return result
    }

    override fun delete(id: Int): Boolean {
        println("HabitEventRepository.delete(id = $id)")
        val query = "delete from habit_event where id = $id"
        dbHelper.executeUpdateQuery(query)
        println("HabitEventRepository.delete(id = $id) = true")
        return true
    }
}