package model;

import java.util.List;

/**
 * Created by blackleones on 20/07/15.
 */
public interface DatabaseManager {
    public void openConnection();
    public void closeConnection();
    public void insertProduct(Product product);
    public void deleteProductWithCode(int code);
    public Product getProductWithCode(int code);
    public List<Product> getAllProduct();
    public void increaseQtaOf(int code, int qta);
    public void decreaseQtaOf(int code, int qta);
}
