package model;

import java.util.List;

/**
 * Created by blackleones on 20/07/15.
 */
public interface DatabaseManager {
    void openConnection();
    void closeConnection();
    void insertProduct(Product product);
    void deleteProductWithCode(String code);
    Product getProductWithCode(String code);
    List<Product> getAllProduct();
    void increaseQtaOf(String code, int qta);
    void decreaseQtaOf(String code, int qta);
}
