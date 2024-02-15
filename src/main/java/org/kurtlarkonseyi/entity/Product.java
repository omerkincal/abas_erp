package org.kurtlarkonseyi.entity;


public class Product {
    private int id;
    private int productNo;
    private int quantity;
    private double unitPrice;
    private int orderId;

    public Product() {

    }
    public Product(int id, int productNo, int quantity, double unitPrice, int orderId) {
        this.id = id;
        this.productNo = productNo;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.orderId = orderId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
