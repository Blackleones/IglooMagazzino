package model;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by blackleones on 20/07/15.
 */
/*
* informazioni sui dati:
* il database è composto da 2 tabelle:
*   ipotesi: il magazzino è unico => non occorre distinzione
*   product: tiene conto di tutti i prodotti che sono stati registrati almeno una volta nel database
*   movement: tiene traccia di tutti gli spostamenti dei prodotti contenuti nel magazzino e che sono stati
*       registrati.
*
*  per poter selezionare la qta di uno specifico prodotto "ddd":
*  select qta from (select code, sum(operation) as qta from movement group by code) where code = "ddd";
* */
public class Database extends Database_Core implements DatabaseManager {
    private static final String CODE = "code";
    private static final String NAME = "name";
    private static final String LIMIT_QTA = "limit_qta";
    private static final String QTA = "qta";
    private static final String OPERATION = "operation";
    private static final String DATE = "timestamp";

    /*
    * inserimento del prodotto se e solo se il suo codice non è già presente all'interno della tabella "product"
    * */
    private static final String query_insert_product = "INSERT INTO product (code, name, limit_qta) " +
            "SELECT ?, ?, ? WHERE NOT EXISTS (SELECT code FROM product " +
            "WHERE code = ?)";

    /*
    * inserimento del movimento se s solo se il codice del prodotto è presente all'interno della tabella "product"
    * */
    private static final String query_insert_movement = "INSERT INTO movement (code, operation, timestamp) " +
            "SELECT ?, ?, ? WHERE EXISTS (SELECT code FROM product " +
            "WHERE code = ?)";

    private static final String query_product_info = "SELECT product.*, SUM(movement.operation) AS qta " +
            "FROM product JOIN movement on product.code = movement.code " +
            "WHERE product.code = ?";

    private final static String query_product_movement =
            "SELECT operation, timestamp " +
                    "FROM movement " +
                    "WHERE code = ? AND code IN (SELECT code FROM product)";

    private PreparedStatement preparedStatement = null;
    private ResultSet queryResult = null;

    public Database() {
        super();
    }

    @Override
    public void openConnection() {
        super.open();
    }

    @Override
    public void closeConnection() {
        super.close();
    }

    @Override
    public void insertNewProduct(Product product) {
        if(product == null)
            throw new IllegalArgumentException();

        try {
            preparedStatement = connection.prepareStatement(query_insert_product);
            preparedStatement.setString(1, product.getCode());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setInt(3, product.getLimit_qta());
            preparedStatement.setString(4, product.getCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void insertNewMovement(String code, Movement movement) {
        if(code == null || movement == null)
            throw new IllegalArgumentException();

        try {
            preparedStatement = connection.prepareStatement(query_insert_movement);
            preparedStatement.setString(1, code);
            preparedStatement.setInt(2, movement.getQta());
            preparedStatement.setString(3, movement.getDate());
            preparedStatement.setString(4, code);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Product> getAllProduct() {
        return null;
    }

}
