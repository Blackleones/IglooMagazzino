package model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by blackleones on 21/07/15.
 */
public class Database_Core {
    private static final String JDBC_CLASS_NAME = "org.sqlite.JDBC";
    private static final String DATABASE_NAME = "igloo_database.db";
    private static final String DB_ADDRESS = "jdbc:sqlite:"+DATABASE_NAME;
    private static final String CREATE_PRODUCT_TABLE =
            "CREATE TABLE IF NOT EXISTS product(" +
                    "code TEXT(13) PRIMARY KEY," +
                    "name TEXT(255) NOT NULL," +
                    "limit_qta INTEGER DEFAULT 5" +
                    ")";

    private static final String CREATE_MOVEMENT_TABLE =
            "CREATE TABLE IF NOT EXISTS movement(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "code TEXT(13) NOT NULL," +
                    "operation INTEGER NOT NULL," +
                    "timestamp long DEFAULT (datetime(CURRENT_TIMESTAMP, 'localtime')) NOT NULL," +
                    "FOREIGN KEY(code) REFERENCES product(code)" +
                    " ON UPDATE CASCADE" +
                    ")";

    protected Connection connection = null;

    public Database_Core(){
        if(databaseExists() == false)
            createDatabase();
    }

    private boolean databaseExists() {
        File f = new File(DATABASE_NAME);
        return f.exists();
    }

    private void createDatabase() {
        System.out.println("Sto creando il database ...");
        open();

        try {
            Statement statement = connection.createStatement();
            statement.execute(CREATE_PRODUCT_TABLE);
            System.out.println("Tabella product creata");
            statement.execute(CREATE_MOVEMENT_TABLE);
            System.out.println("Tabella movement creata");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Database creato con successo.");
        close();
    }

    protected void open(){
        try {
            Class.forName(JDBC_CLASS_NAME);
            connection = DriverManager.getConnection(DB_ADDRESS);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void close(){
        try {
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
