package pl.gawor.taycknerdesktopclient.repository

import pl.gawor.taycknerdesktopclient.repository.dbhelper.DbHelper
import pl.gawor.taycknerdesktopclient.repository.entity.ScheduleEntity
import java.time.LocalDate
import java.time.temporal.TemporalQuery

class ScheduleRepository : ICrudRepository<ScheduleEntity> {

    private val dbHelper = DbHelper()

    override fun list(): List<ScheduleEntity>? {
        val query = "select * from schedule"
       return listQuery(query)
    }

    override fun create(entity: ScheduleEntity): ScheduleEntity? {
        val query =
            "insert into schedule value (0, '${entity.name}', '${entity.startTime}', '${entity.endTime}', ${entity.duration}, ${entity.userId})"
        val id = dbHelper.executeInsertQuery(query)

        return read(id)
    }

    override fun read(id: Int): ScheduleEntity? {
        val query = "select * from schedule where id = $id"
        val resultSet = dbHelper.executeResultQuery(query)
        if (resultSet != null && resultSet.next()) {
            val entity: ScheduleEntity?
            entity = ScheduleEntity(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getTimestamp("start_time").toLocalDateTime(),
                resultSet.getTimestamp("end_time").toLocalDateTime(),
                resultSet.getDouble("duration"),
                resultSet.getInt("user_id")
            )
            return entity
        }
        return null
    }

    override fun update(id: Int, entity: ScheduleEntity): ScheduleEntity? {
        val query =
            "update schedule set name = '${entity.name}', start_time = '${entity.startTime}', end_time = '${entity.endTime}', duration = ${entity.duration}, user_id = ${entity.userId} where id = $id;"
        dbHelper.executeUpdateQuery(query)
        return read(id)
    }

    override fun delete(id: Int): Boolean {
        val query = "delete from schedule where id = $id"
        dbHelper.executeUpdateQuery(query)
        return true
    }

    fun list(date: LocalDate): List<ScheduleEntity>? {
        val query = "select * from schedule where start_time like '%$date%'"
        return listQuery(query)
    }

    private fun listQuery(query: String) : List<ScheduleEntity>? {
        val list = ArrayList<ScheduleEntity>()
        val resultSet = dbHelper.executeResultQuery(query)

        if (resultSet != null) {
            var entity: ScheduleEntity?
            while (resultSet.next()) {
                entity = ScheduleEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getTimestamp("start_time").toLocalDateTime(),
                    resultSet.getTimestamp("end_time").toLocalDateTime(),
                    resultSet.getDouble("duration"),
                    resultSet.getInt("user_id")
                )
                list.add(entity)
            }
            return list
        }
        return null
    }
}