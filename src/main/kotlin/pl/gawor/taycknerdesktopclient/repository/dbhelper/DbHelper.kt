package pl.gawor.taycknerdesktopclient.repository.dbhelper

import java.sql.*

private const val DB_URL = "jdbc:mysql://localhost:3306/tayckner_desktop_db?serverTimezone=UTC"
private const val DB_USER = "root"
private const val DB_PASSWORD = "ejek"

class DbHelper {

    private fun getConnection(): Connection? {
        val connection: Connection
        return try {
            connection = DriverManager.getConnection(
                DB_URL, DB_USER, DB_PASSWORD
            )
            connection
        } catch (ex: Exception) {
            println("Error: " + ex.message)
            null
        }
    }

    fun executeUpdateQuery(query: String) {
        val connection = getConnection()
        val statement: Statement
        try {
            statement = connection!!.createStatement()
            statement.executeUpdate(query)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    fun executeResultQuery(query: String): ResultSet? {
        val connection = getConnection()
        val statement: Statement
        var resultSet: ResultSet? = null
        try {
            statement = connection!!.createStatement()
            resultSet = statement.executeQuery(query)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return resultSet
    }

    fun executeInsertQuery(query: String): Int {
        val connection = getConnection()
        val statement: PreparedStatement = connection!!.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        statement.execute()

        val resultSet = statement.generatedKeys
        var generatedKey = 0
        if (resultSet.next()) {
            generatedKey = resultSet.getInt(1)
        }
        return generatedKey
    }
}