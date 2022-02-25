package pl.gawor.taycknerdesktopclient.repository

import pl.gawor.taycknerdesktopclient.repository.dbhelper.DbHelper
import pl.gawor.taycknerdesktopclient.repository.entity.ScheduleEntity
import java.time.LocalDate

class ScheduleRepository : ICrudRepository<ScheduleEntity> {

    private val dbHelper = DbHelper()

    override fun list(): List<ScheduleEntity>? {
        val query = "select * from schedule"
       return listQuery(query)
    }

    override fun create(entity: ScheduleEntity): ScheduleEntity? {
        val startTime = if (entity.startTime == null) "null" else "'${entity.startTime}'"
        val endTime = if (entity.endTime == null) "null" else "'${entity.endTime}'"
        val duration = if (entity.duration == null) "null" else "'${entity.duration}'"
        val query =
            "insert into schedule value (0, '${entity.name}', $startTime, $endTime, $duration, ${entity.userId})"
        val id = dbHelper.executeInsertQuery(query)

        return read(id)
    }

    override fun read(id: Int): ScheduleEntity? {
        val query = "select * from schedule where id = $id"
        val resultSet = dbHelper.executeResultQuery(query)
        if (resultSet != null && resultSet.next()) {
            val entity: ScheduleEntity?
            val startTime = if (resultSet.getTimestamp("start_time") == null) null else resultSet.getTimestamp("start_time").toLocalDateTime()
            val endTime = if (resultSet.getTimestamp("end_time") == null) null else resultSet.getTimestamp("end_time").toLocalDateTime()
            var duration: Double? = resultSet.getDouble("duration")
            if (resultSet.wasNull()) duration = null
            entity = ScheduleEntity(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                startTime,
                endTime,
                duration,
                resultSet.getInt("user_id")
            )
            println(entity)
            return entity
        }
        return null
    }

    override fun update(id: Int, entity: ScheduleEntity): ScheduleEntity? {
        val startTime = if (entity.startTime == null) "null" else "'${entity.startTime}'"
        val endTime = if (entity.endTime == null) "null" else "'${entity.endTime}'"
        val duration = if (entity.duration == null) "null" else "'${entity.duration}'"
        val query =
            "update schedule set name = '${entity.name}', start_time = $startTime, end_time = '$endTime, duration = $duration, user_id = ${entity.userId} where id = $id;"
        dbHelper.executeUpdateQuery(query)
        return read(id)
    }

    override fun delete(id: Int): Boolean {
        val query = "delete from schedule where id = $id"
        dbHelper.executeUpdateQuery(query)
        return true
    }

    fun list(date: LocalDate): List<ScheduleEntity>? {
        val query = "select * from schedule where start_time like '%$date%' OR end_time like '%$date%'"
        return listQuery(query)
    }

    private fun listQuery(query: String) : List<ScheduleEntity>? {
        val list = ArrayList<ScheduleEntity>()
        val resultSet = dbHelper.executeResultQuery(query)

        if (resultSet != null) {
            var entity: ScheduleEntity?
            while (resultSet.next()) {
                // `.minusHours(1)` at the end is bcuz MySQL for some unknown reason was returning inflated (by one hour) timestamps in resultSet
                val startTime = if (resultSet.getTimestamp("start_time") == null) null else resultSet.getTimestamp("start_time").toLocalDateTime().minusHours(1)
                val endTime = if (resultSet.getTimestamp("end_time") == null) null else resultSet.getTimestamp("end_time").toLocalDateTime().minusHours(1)
                var duration: Double? = resultSet.getDouble("duration")
                if (resultSet.wasNull()) duration = null
                entity = ScheduleEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    startTime,
                    endTime,
                    duration,
                    resultSet.getInt("user_id")
                )
                println(entity)
                list.add(entity)
            }
            return list
        }
        return null
    }
}