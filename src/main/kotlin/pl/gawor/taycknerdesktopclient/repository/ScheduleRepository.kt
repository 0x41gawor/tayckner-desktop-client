package pl.gawor.taycknerdesktopclient.repository

import pl.gawor.taycknerdesktopclient.repository.dbhelper.DbHelper
import pl.gawor.taycknerdesktopclient.repository.entity.Schedule
import pl.gawor.taycknerdesktopclient.repository.entity.User

class ScheduleRepository : ICrudRepository<Schedule> {

    private val dbHelper = DbHelper()

    override fun list(): List<Schedule>? {
        val list = ArrayList<Schedule>()
        val query = "select * from schedule"
        val resultSet = dbHelper.executeResultQuery(query)

        if (resultSet != null) {
            var entity: Schedule?
            while (resultSet.next()) {
                entity = Schedule(
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

    override fun create(entity: Schedule): Schedule? {
        val query =
            "insert into schedule value (0, '${entity.name}', '${entity.startTime}', '${entity.endTime}', ${entity.duration}, ${entity.userId})"
        val id = dbHelper.executeInsertQuery(query)

        return read(id)
    }

    override fun read(id: Int): Schedule? {
        val query = "select * from schedule where id = $id"
        val resultSet = dbHelper.executeResultQuery(query)
        if (resultSet != null && resultSet.next()) {
            val entity: Schedule?
            entity = Schedule(
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

    override fun update(id: Int, entity: Schedule): Schedule? {
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
}