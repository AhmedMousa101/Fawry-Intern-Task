package model;

import policy.expire.ExpirePolicy;
import policy.shipping.ShippingPolicy;

public class Product {
    private String name;
    private double price;
    private int quantity;
    private ExpirePolicy expirePolicy;
    private ShippingPolicy shippingPolicy;

    public Product(String name, double price, int quantity, ExpirePolicy expirePolicy, ShippingPolicy shippingPolicy) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.expirePolicy = expirePolicy;
        this.shippingPolicy = shippingPolicy;
    }

    public boolean isExpired() {
        return expirePolicy.isExpired();
    }

    public boolean isShippable() {
        return shippingPolicy.requiresShipping();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getWeight(){
        return shippingPolicy.getWeight();
    }

    public void reduceQuantity(int quantity){
        this.quantity -= quantity;
    }

}
