package model;

import java.util.List;

/**
 * Created by blackleones on 20/07/15.
 */
public interface DatabaseManager {
    /*
    * @do: apre la connessione con il database
    * */
    void openConnection();
    /*
    * @do: chiude la connessione con il database
    * */
    void closeConnection();
    /*
    * @do: product viene inserito nella tabella "product" se e solo se non è gia stato inserito
    * @param: product, le informazioni del prodotto da memorizzare
    * @throws: viene lanciato IllegalArgumentException se e solo se il parametro è == null
    * */
    void insertNewProduct(Product product) throws IllegalArgumentException;
    /*
    * @do: inserisce un nuovo movimento, relativo al prodotto identificato da code, all'interno della tabella movement
    * @param: code, il codice del prodotto interessato
    *       movement: il movimento da salvare
    * @throws: viene lanciato IllegalArgumentException se e solo se uno dei parametri è == null
    * */
    void insertNewMovement(String code, Movement movement) throws IllegalArgumentException;
    /*
    * @do: ritorna una lista contenente tutte le informazioni relative ai prodotti contenuti nel database
    * */
    List<Product> getAllProduct();
}
