package controller;

import model.Movement;
import model.Product;

import java.util.HashMap;

/**
 * Created by blackleones on 29/07/15.
 */
public interface ProductManager {
    /*
    * @do: apre la connessione con il database
    * */
    void open();
    /*
    * @do: chiude la connessione con il database
    * */
    void close();
    /*
    * @param: code, codice del prodotto
    * @param: name, nome del prodotto
    * @param: limit_qta, limite oltre il quale dobbiamo avvisare l'utente
    * @do: inserisce il prodotto all'interno della struttura dati
    * @throws: IllegalArgumentException nel caso uno degli argomenti sia uguale a null
    * */
    void insertProduct(String code, String name, int limit_qta) throws IllegalArgumentException;
    /*
    * @param: code, codice del prodotto
    * @param: qta, la quantità interessata nel movimento
    * @param: reason, il motivo del movimento
    * @do: crea ed inserisce il movimento all'interno della struttura dati
    * @throws: IllegalArgumentException nel caso uno degli argomenti sia uguale a null
    * */
    void insertMovement(String code, int qta, String reason) throws IllegalArgumentException;
    /*
    * @do: prepara le strutture dati
    * */
    void loadDataFromDatabase();
    /*
    * @do: ritorna la struttura dati dei prodotti in magazzino
    * */
    HashMap<String, Product> getStoredProduct();
    /*
    * @do: ritorna la struttura dati di tutti i prodotti
    * */
    HashMap<String, Product> getAllProduct();
}
