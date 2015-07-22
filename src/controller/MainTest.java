package controller;

import model.Database;
import model.DatabaseManager;
import model.Product;

/**
 * Created by blackleones on 20/07/15.
 */
public class MainTest {
    public static void main(String args[]){
        DatabaseManager dbManager = new Database();
        dbManager.openConnection();
        dbManager.insertProduct(new Product("ddd", "nome", 40, 3));
        System.out.println(dbManager.getProductWithCode("ddd"));
        dbManager.closeConnection();
        System.out.println("Fine test.");
    }
}
