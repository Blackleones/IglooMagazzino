package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by blackleones on 28/07/15.
 */
public class Product implements Cloneable{
    private String code;
    private String name;
    private int qta;
    private int limit_qta;
    private List<Movement> movements;

    public Product(String code, String name, int limit_qta){
        this.code = code;
        this.name = name;
        this.limit_qta = limit_qta;
        movements = new ArrayList<Movement>();
    }

    public Product(String code, String name, int limit_qta, List<Movement> movements){
        this(code, name, limit_qta);
        this.movements = movements;
    }

    public void updateQta(int update){
        qta += update;
    }

    public void insertMovement(Movement movement){
        movements.add(movement);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Product(code, name, limit_qta, movements);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
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

    public void modifyQta(int qta) {
        this.qta = qta;
    }
}
