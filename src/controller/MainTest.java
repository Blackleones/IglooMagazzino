package controller;

import model.Database;
import model.DatabaseManager;
import model.Movement;
import model.Product;
import utils.Timestamp_util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by blackleones on 20/07/15.
 */
public class MainTest {
    public static void main(String args[]){
        DatabaseManager dbManager = new Database();
        dbManager.openConnection();

        List<Movement> m = new ArrayList();
        m.add(new Movement(40));
        Product product = new Product("a", "a", 40, 3, m);

        dbManager.insertNewProduct(product);
        dbManager.insertNewMovement(product.getCode(), m.get(0));

        dbManager.closeConnection();
        System.out.println("Fine test.");
    }
}
