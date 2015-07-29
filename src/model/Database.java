package model;

import java.sql.*;
import java.util.*;

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
    private static final String ID = "id";
    private static final String PRODUCT_ID = "product_id";
    private static final String NAME = "name";
    private static final String LIMIT_QTA = "limit_qta";
    private static final String QTA = "qta";
    private static final String OPERATION = "operation";
    private static final String DATE = "date";
    private static final String REASON = "reason";

    /*
    * inserimento del prodotto se e solo se il suo codice non è già presente all'interno della tabella "product"
    * */
    private static final String query_insert_product = "INSERT INTO product (id, name, limit_qta) " +
            "SELECT ?, ?, ? WHERE NOT EXISTS (SELECT id FROM product " +
            "WHERE id = ?)";

    /*
    * inserimento del movimento se s solo se il codice del prodotto è presente all'interno della tabella "product"
    * */
    private static final String query_insert_movement = "INSERT INTO movement (product_id, operation, reason, date) " +
            "SELECT ?, ?, ?, ? WHERE EXISTS (SELECT id FROM product " +
            "WHERE id = ?)";

    private static final String query_get_all_product = "SELECT * FROM product";

    private static final String query_get_all_movement = "SELECT * FROM movement ORDER BY movement.date AND movement.product_id";

    private static final String query_get_all_stored_product = "SELECT product.*, qta FROM product " +
            "INNER JOIN in_store ON product.id = in_store.product_id ORDER BY id";

    private static final String query_get_all_movement_stored_product = "SELECT movement.product_id, operation, reason, date " +
            "FROM movement INNER JOIN in_store ON movement.product_id = in_store.product_id ORDER BY movement.date AND movement.product_id";

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
    public void insertProduct(Product product) {
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
    public void insertMovement(String code, Movement movement) {
        if(code == null || movement == null)
            throw new IllegalArgumentException();

        try {
            preparedStatement = connection.prepareStatement(query_insert_movement);
            preparedStatement.setString(1, code);
            preparedStatement.setInt(2, movement.getQta());
            preparedStatement.setString(3, movement.getReason());
            preparedStatement.setString(4, movement.getDate());
            preparedStatement.setString(5, code);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public HashMap<String, Product> getAllStoredProduct() {
        HashMap<String, Product> products = new HashMap<String, Product>();

        try {
            preparedStatement = connection.prepareStatement(query_get_all_stored_product);
            queryResult = preparedStatement.executeQuery();

            while(queryResult.next()){
                products.put(queryResult.getString(ID),
                        new Product(
                                queryResult.getString(ID),
                                queryResult.getString(NAME),
                                queryResult.getInt(LIMIT_QTA)
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            preparedStatement = connection.prepareStatement(query_get_all_movement_stored_product);
            queryResult = preparedStatement.executeQuery();

            while(queryResult.next()) {
                products.get(queryResult.getString(PRODUCT_ID))
                        .insertMovement(
                                new Movement(
                                        queryResult.getInt(OPERATION),
                                        queryResult.getString(REASON),
                                        queryResult.getString(DATE)
                                )
                        );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new HashMap<String, Product>(products);
    }

    @Override
    public HashMap<String, Product> getAllProduct() {
        HashMap<String, Product> products = new HashMap<String, Product>();

        try {
            preparedStatement = connection.prepareStatement(query_get_all_product);
            queryResult = preparedStatement.executeQuery();

            while(queryResult.next()){
                products.put(queryResult.getString(ID),
                        new Product(
                                queryResult.getString(ID),
                                queryResult.getString(NAME),
                                queryResult.getInt(LIMIT_QTA)
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            preparedStatement = connection.prepareStatement(query_get_all_movement);
            queryResult = preparedStatement.executeQuery();

            while(queryResult.next()){
                products.get(queryResult.getString(PRODUCT_ID))
                        .insertMovement(
                                new Movement(
                                        queryResult.getInt(OPERATION),
                                        queryResult.getString(REASON),
                                        queryResult.getString(DATE)
                                )
                        );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new HashMap<String, Product>(products);
    }


}
