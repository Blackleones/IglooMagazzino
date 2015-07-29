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
                    "id TEXT(13) PRIMARY KEY," +
                    "name TEXT(255) NOT NULL," +
                    "limit_qta INTEGER DEFAULT 5" +
                    ")";

    private static final String CREATE_MOVEMENT_TABLE =
            "CREATE TABLE IF NOT EXISTS movement(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "product_id TEXT(13) NOT NULL," +
                    "operation INTEGER NOT NULL," +
                    "reason TEXT NOT NULL," +
                    "date TIMESTAMP NOT NULL," +
                    "FOREIGN KEY (product_id) REFERENCES product(id) ON UPDATE CASCADE" +
                    ")";

    private static final String CREATE_IN_STORE_TABLE =
            "CREATE TABLE IF NOT EXISTS in_store(" +
                    "product_id TEXT(13) PRIMARY KEY," +
                    "qta INTEGER NOT NULL," +
                    "FOREIGN KEY(product_id) REFERENCES product(id) ON UPDATE CASCADE" +
                    ")";

    private static final String CREATE_UPDATE_QTA_TRIGGER =
            "CREATE TRIGGER IF NOT EXISTS update_qta AFTER INSERT ON movement " +
                    "BEGIN " +
                    "INSERT OR REPLACE INTO in_store VALUES " +
                    "(NEW.product_id, COALESCE((SELECT qta + NEW.operation FROM in_store WHERE product_id = NEW.product_id), " +
                                                "NEW.operation)); " +
                    "END;";

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
            System.out.println("Tabella product creata.");
            statement.execute(CREATE_MOVEMENT_TABLE);
            System.out.println("Tabella movement creata.");
            statement.execute(CREATE_IN_STORE_TABLE);
            System.out.println("Tabella in_store creata.");
            statement.execute(CREATE_UPDATE_QTA_TRIGGER);
            System.out.println("Trigger update_qta creato.");
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
