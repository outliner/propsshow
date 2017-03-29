package com.outliner.props.modulecommodityprops.modle;


public class BaseSkuModel {

    //base 属性
    private double price;//价格
    private long stock;//库存

    public BaseSkuModel(double price, long stock) {
        this.price = price;
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }
}
