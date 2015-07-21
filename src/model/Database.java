package model;

import java.sql.*;

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

    private String query_product = "INSERT INTO product (code, name, limit_qta) " +
            "SELECT ?, ?, ? WHERE NOT EXISTS (SELECT code FROM product " +
            "WHERE code = ?)";

    private String query_movement = "INSERT INTO movement (code, operation) " +
            "SELECT ?, ? WHERE EXISTS (SELECT code FROM product " +
            "WHERE code= ?)";

    private PreparedStatement preparedStatement = null;

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
        /*
        * se il prodotto è nuovo verrà inserito all'interno della tabella prodotti
        * */
        try {
            preparedStatement = connection.prepareStatement(query_product);
            preparedStatement.setString(1, product.getCode());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setInt(3, product.getLimit_qta());
            preparedStatement.setString(4, product.getCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
        * se il prodotto è gia stato registrato all'interno della tabella prodotti allora
        * salvo il suo "movimento" all'interno della tabella movimenti
        * */
        try {
            preparedStatement = connection.prepareStatement(query_movement);
            preparedStatement.setString(1, product.getCode());
            preparedStatement.setInt(2, product.getQta());
            preparedStatement.setString(3, product.getCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteProductWithCode(int code) {

    }

    @Override
    public Product getProductWithCode(int code) {
        return null;
    }

    @Override
    public List<Product> getAllProduct() {
        return null;
    }

    @Override
    public void increaseQtaOf(int code, int qta) {

    }

    @Override
    public void decreaseQtaOf(int code, int qta) {

    }
}
