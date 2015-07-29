package controller;


import model.Database;
import model.DatabaseManager;
import model.Product;

import java.util.HashMap;

/**
 * Created by blackleones on 20/07/15.
 */
public class MainTest {
    public static void main(String args[]){
        ProductManager pm = new Manager();

        pm.open();
        pm.loadDataFromDatabase();
        pm.modifyQta("002", 60);
        pm.modifyQta("004", 40);
        pm.close();
        System.out.println("Fine test.");
    }
}
