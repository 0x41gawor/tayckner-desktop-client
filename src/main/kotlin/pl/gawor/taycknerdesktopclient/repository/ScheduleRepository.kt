package pl.gawor.taycknerdesktopclient.repository

import pl.gawor.taycknerdesktopclient.repository.dbhelper.DbHelper
import pl.gawor.taycknerdesktopclient.repository.entity.ScheduleEntity
import java.time.LocalDate

class ScheduleRepository : ICrudRepository<ScheduleEntity> {

    private val dbHelper = DbHelper()

    override fun list(): List<ScheduleEntity>? {
        println("ScheduleRepository.list()")
        val query = "select * from schedule"
        val result = listQuery(query)
        println("ScheduleRepository.list() = $result")
        return result
    }

    override fun create(entity: ScheduleEntity): ScheduleEntity? {
        println("ScheduleRepository.create(entity = $entity)")
        val startTime = if (entity.startTime == null) "null" else "'${entity.startTime}'"
        val endTime = if (entity.endTime == null) "null" else "'${entity.endTime}'"
        val duration = if (entity.duration == null) "null" else "'${entity.duration}'"
        val query =
            "insert into schedule value (0, '${entity.name}', $startTime, $endTime, '${entity.date}', $duration, ${entity.userId})"
        val id = dbHelper.executeInsertQuery(query)
        val result = read(id)
        println("ScheduleRepository.create(entity = $entity) = $result")
        return result
    }

    override fun read(id: Int): ScheduleEntity? {
        println("ScheduleRepository.read(id = $id)")
        val query = "select * from schedule where id = $id"
        val resultSet = dbHelper.executeResultQuery(query)
        val result: ScheduleEntity?
        if (resultSet != null && resultSet.next()) {
            val entity: ScheduleEntity?
            val startTime = if (resultSet.getTimestamp("start_time") == null) null else resultSet.getTimestamp("start_time").toLocalDateTime().toLocalTime()
            val endTime = if (resultSet.getTimestamp("end_time") == null) null else resultSet.getTimestamp("end_time").toLocalDateTime().toLocalTime()
            var duration: Double? = resultSet.getDouble("duration")
            if (resultSet.wasNull()) duration = null
            entity = ScheduleEntity(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                startTime,
                endTime,
                resultSet.getDate("date").toLocalDate(),
                duration,
                resultSet.getInt("user_id")
            )
            result = entity
        } else result = null
        println("ScheduleRepository.read(id = $id) = $result")
        return result
    }

    override fun update(id: Int, entity: ScheduleEntity): ScheduleEntity? {
        println("ScheduleRepository.update(id = $id, entity = $entity)")
        val startTime = if (entity.startTime == null) "null" else "'${entity.startTime}'"
        val endTime = if (entity.endTime == null) "null" else "'${entity.endTime}'"
        val duration = if (entity.duration == null) "null" else "'${entity.duration}'"
        val query =
            "update schedule set name = '${entity.name}', start_time = $startTime, end_time = $endTime, duration = $duration, user_id = ${entity.userId} where id = $id;"
        dbHelper.executeUpdateQuery(query)
        val result = read(id)
        println("ScheduleRepository.update(id = $id, entity = $entity) = $result")
        return result
    }

    override fun delete(id: Int): Boolean {
        println("ScheduleRepository.delete(id = $id)")
        val query = "delete from schedule where id = $id"
        dbHelper.executeUpdateQuery(query)
        println("ScheduleRepository.delete(id = $id) = true")
        return true
    }

    fun list(date: LocalDate): List<ScheduleEntity>? {
        println("ScheduleRepository.list(date = $date)")
        val query = "select * from schedule where date like '%$date%'"
        val result = listQuery(query)
        println("ScheduleRepository.list(date = $date) = $result")
        return result
    }

    private fun listQuery(query: String) : List<ScheduleEntity>? {
        val list = ArrayList<ScheduleEntity>()
        val resultSet = dbHelper.executeResultQuery(query)

        if (resultSet != null) {
            var entity: ScheduleEntity?
            while (resultSet.next()) {
                // `.minusHours(1)` at the end is bcuz MySQL for some unknown reason was returning inflated (by one hour) timestamps in resultSet
                val startTime = if (resultSet.getTimestamp("start_time") == null) null else resultSet.getTimestamp("start_time").toLocalDateTime().minusHours(1).toLocalTime()
                val endTime = if (resultSet.getTimestamp("end_time") == null) null else resultSet.getTimestamp("end_time").toLocalDateTime().minusHours(1).toLocalTime()
                var duration: Double? = resultSet.getDouble("duration")
                if (resultSet.wasNull()) duration = null
                entity = ScheduleEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    startTime,
                    endTime,
                    resultSet.getDate("date").toLocalDate(),
                    duration,
                    resultSet.getInt("user_id")
                )
                list.add(entity)
            }
            return list
        }
        return null
    }
}