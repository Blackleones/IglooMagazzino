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
        pm.close();
        System.out.println("Fine test.");
    }
}
