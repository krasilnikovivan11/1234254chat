package database;

import java.sql.*;

public class ConnectionDB {
    public static Connection connection;
    public static Statement statement;
    public static PreparedStatement preparedStatement;

    public ConnectionDB() throws ClassNotFoundException, SQLException {
        connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:TEST1.s3db");
        statement = connection.createStatement();
        System.out.println("База Данных подключена");
        createTable();
        appData("login","pass");

    }

    private void dropTable() throws SQLException {
        statement.execute("drop table users");
    }

    public static void createTable() throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY AUTOINCREMENT, login text, pass text);");
        System.out.println("Таблица создана");
    }

    public static void appData(String login, String pass) throws SQLException {
        statement.execute("INSERT INTO users(login, pass) VALUES ('" + login + "', '" + pass + "');");
        System.out.println("Added");
    }
    public static void appData1() throws SQLException {
        statement.execute("INSERT INTO users( login, pass) VALUES ('1', '1');");
    }

    public static boolean searchClient(String login, String pass) {
        try {
            statement.execute("select * from users where " + "login == '" + login + "' and pass == '" + pass + "';");
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }
}
