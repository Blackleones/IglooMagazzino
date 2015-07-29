package controller;

import model.Database;
import model.DatabaseManager;
import model.Movement;
import model.Product;

import java.util.HashMap;

/**
 * Created by blackleones on 29/07/15.
 */
public class Manager implements ProductManager {
    DatabaseManager dbManager = null;
    HashMap<String, Product> storedProducts = null;
    HashMap<String, Product> allProducts = null;
    public Manager(){
        dbManager = new Database();
    }

    @Override
    public void open() {
        dbManager.openConnection();
    }

    @Override
    public void close() {
        dbManager.closeConnection();
    }

    @Override
    public void insertProduct(String code, String name, int limit_qta) {
        if(code == null || name == null)
            throw new IllegalArgumentException();

        Product product = new Product(code, name, limit_qta);
        dbManager.insertProduct(product);

        if(!storedProducts.containsKey(code))
            storedProducts.put(code, product);

        if(!allProducts.containsKey(code))
            allProducts.put(code, product);

    }

    @Override
    public void insertMovement(String code, int qta, String reason) {
        if(code == null || reason == null)
            throw new IllegalArgumentException();

        Movement movement = new Movement(qta, reason);

        if(!storedProducts.containsKey(code))
            storedProducts.put(code, allProducts.get(code));

        storedProducts.get(code).insertMovement(movement);
        dbManager.insertMovement(code, movement);
    }

    @Override
    public void loadDataFromDatabase() {
        storedProducts = dbManager.getAllStoredProduct();
        allProducts =  dbManager.getAllProduct();
    }

    @Override
    public void modifyQta(String code, int qta){
        if(!storedProducts.containsKey(code))
            storedProducts.put(code, allProducts.get(code));

        Product p = storedProducts.get(code);
        int diff = qta - p.getQta();
        p.modifyQta(qta);

        insertMovement(code, diff, Movement.MODIFY);
    }

    @Override
    public HashMap<String, Product> getStoredProduct() {
        return storedProducts;
    }

    @Override
    public HashMap<String, Product> getAllProduct() {
        return allProducts;
    }

}
