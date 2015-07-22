package model;

import java.util.List;

/**
 * Created by blackleones on 14/07/15.
 */
public class Product {
    private String code;
    private String name;
    private int qta;
    private int limit_qta;

    private List<Movement> movements;

    public Product(String code, String name, int qta, int limit_qta){
        this.code = code;
        this.name = name;
        this.qta = qta;
        this.limit_qta = limit_qta;
        this.movements = null;
    }

    public Product(String code, String name, int qta, int limit_qta, List<Movement> movements)
    {
        this(code, name, qta, limit_qta);
        this.movements = movements;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getQta() {
        return qta;
    }

    public int getLimit_qta() {
        return limit_qta;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", qta=" + qta +
                ", limit_qta=" + limit_qta +
                ", \nmovements=" + movements +
                '}'+"\n";
    }
}
