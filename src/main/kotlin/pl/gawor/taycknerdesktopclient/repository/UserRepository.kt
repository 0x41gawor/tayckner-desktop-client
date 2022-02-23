package pl.gawor.taycknerdesktopclient.repository

import pl.gawor.taycknerdesktopclient.repository.dbhelper.DbHelper
import pl.gawor.taycknerdesktopclient.repository.entity.User

class UserRepository : ICrudRepository<User> {

    private val dbHelper = DbHelper()

    override fun list(): List<User>? {
        val list = ArrayList<User>()
        val query = "select * from user"
        val resultSet = dbHelper.executeResultQuery(query)

        if (resultSet != null) {
            var entity: User?
            while (resultSet.next()) {
                entity = User(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email")
                )
                list.add(entity)
            }
            return list
        }
        return null
    }

    override fun create(entity: User): User? {
        val query =
            "insert into user value (0, '${entity.username}', '${entity.password}', '${entity.firstName}', '${entity.lastName}', '${entity.email}')"
        val id = dbHelper.executeInsertQuery(query)

        return read(id)
    }

    override fun read(id: Int): User? {
        val query = "select * from user where id = $id"
        val resultSet = dbHelper.executeResultQuery(query)
        if (resultSet != null && resultSet.next()) {
            val entity: User?
            entity = User(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email")
            )
            return entity
        }
        return null
    }

    override fun update(id: Int, entity: User): User? {
        val query =
            "update user set username = '${entity.username}', password = '${entity.password}', first_name = '${entity.firstName}', " +
                    "last_name = '${entity.lastName}', email = '${entity.email}' where id = $id"
        dbHelper.executeUpdateQuery(query)
        return read(id)
    }

    override fun delete(id: Int): Boolean {
        val query = "delete from user where id = $id"
        dbHelper.executeUpdateQuery(query)
        return true
    }

}