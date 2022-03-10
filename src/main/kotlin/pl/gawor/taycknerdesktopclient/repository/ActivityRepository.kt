package pl.gawor.taycknerdesktopclient.repository

import pl.gawor.taycknerdesktopclient.repository.dbhelper.DbHelper
import pl.gawor.taycknerdesktopclient.repository.entity.ActivityEntity
import java.time.LocalDate

class ActivityRepository : ICrudRepository<ActivityEntity> {

    private val dbHelper = DbHelper()

    override fun list(): List<ActivityEntity>? {
        println("ActivityRepository.list()")
        val query = "select * from activity"
        val result = listQuery(query)
        println("ActivityRepository.list() = $result")
        return result
    }

    override fun create(entity: ActivityEntity): ActivityEntity? {
        println("ActivityRepository.create(entity = $entity)")
        val endTime = if (entity.endTime == null) "null" else "'${entity.endTime}'"
        val query = "INSERT INTO activity VALUE (0, '${entity.name}', '${entity.startTime}', $endTime, '${entity.date}', ${entity.duration}, ${entity.breaks}, ${entity.categoryId})"
        val id = dbHelper.executeInsertQuery(query)
        val result = read(id)
        println("ActivityRepository.create(entity = $entity) = $result")
        return result
    }

    override fun read(id: Int): ActivityEntity? {
        println("ActivityRepository.read(id = $id)")
        val query = "select * from activity where id = $id"
        val resultSet = dbHelper.executeResultQuery(query)
        val result: ActivityEntity?
        if (resultSet != null && resultSet.next()) {
            val entity: ActivityEntity?
            val endTime = if (resultSet.getTimestamp("end_time") == null) null else resultSet.getTimestamp("end_time").toLocalDateTime().toLocalTime()
            entity = ActivityEntity(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getTimestamp("start_time").toLocalDateTime().toLocalTime(),
                endTime,
                resultSet.getDate("date").toLocalDate(),
                resultSet.getInt("duration"),
                resultSet.getInt("breaks"),
                resultSet.getInt("category_id")
            )
            result = entity
        } else result = null
        println("ActivityRepository.read(id = $id) = $result")
        return result
    }

    override fun update(id: Int, entity: ActivityEntity): ActivityEntity? {
        println("ActivityRepository.update(id = $id, entity = $entity)")
        val endTime = if (entity.endTime == null) "null" else "'${entity.endTime}'"
        val query = "update activity set name = '${entity.name}', start_time = '${entity.startTime}', end_time = $endTime, date = '${entity.date}', duration = ${entity.duration}, breaks = ${entity.breaks}, category_id = ${entity.categoryId} WHERE id = $id;"
        dbHelper.executeUpdateQuery(query)
        val result = read(id)
        println("ActivityRepository.update(id = $id, entity = $entity) = $result")
        return result
    }

    override fun delete(id: Int): Boolean {
        println("ActivityRepository.delete(id = $id)")
        val query = "delete from activity where id = $id"
        dbHelper.executeUpdateQuery(query)
        println("ActivityRepository.delete(id = $id) = true")
        return true
    }

    fun list(date: LocalDate): List<ActivityEntity>? {
        println("ActivityRepository.list(date = $date)")
        val query = "select * from activity where date like '%$date%'"
        val result = listQuery(query)
        println("ActivityRepository.list(date = $date) = $result")
        return result
    }

    private fun listQuery(query: String): List<ActivityEntity>? {
        val list = ArrayList<ActivityEntity>()
        val resultSet = dbHelper.executeResultQuery(query)

        if (resultSet != null) {
            var entity: ActivityEntity?
            while (resultSet.next()) {
                val endTime = if (resultSet.getTimestamp("end_time") == null) null else resultSet.getTimestamp("end_time").toLocalDateTime().toLocalTime()
                entity = ActivityEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getTimestamp("start_time").toLocalDateTime().toLocalTime(),
                    endTime,
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getInt("duration"),
                    resultSet.getInt("breaks"),
                    resultSet.getInt("category_id")
                )
                list.add(entity)
            }
            return list
        }
        return null
    }
}