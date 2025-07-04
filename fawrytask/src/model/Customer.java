package model;

import service.Shippable;
import service.ShippingService;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private double balance;
    private List<CartItem> cart = new ArrayList<CartItem>();

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }
    public void addToCart(Product product, int quantity) {
        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Not enough quantity for product: " + product.getName());
        }
        cart.add(new CartItem(product, quantity));
    }
    public double getBalance() {
        return balance;
    }
    public void checkout() {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cart is empty!");
        }

        double subtotal = 0;
        double shippingFees = 0;
        List<Shippable> shippableItems = new ArrayList<>();

        for (CartItem item : cart) {
            Product product = item.getProduct();

            if (product.isExpired()) {
                throw new IllegalStateException("model.Product is expired: " + product.getName());
            }

            if (item.getQuantity() > product.getQuantity()) {
                throw new IllegalStateException("model.Product out of stock: " + product.getName());
            }

            subtotal += item.getPrice();

            if (product.isShippable()) {
                shippingFees += 10.0;
                shippableItems.add(new Shippable() {
                    public String getName() { return product.getName(); }
                    public double getWeight() { return product.getWeight(); }
                });
            }
        }

        double total = subtotal + shippingFees;

        if (balance < total) {
            throw new IllegalStateException("Insufficient balance. Needed: " + total + ", Available: " + balance);
        }

        for (CartItem item : cart) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }

        balance -= total;

        if (!shippableItems.isEmpty()) {
            ShippingService.ship(shippableItems);
        }

        System.out.println("------ Checkout Summary ------");
        System.out.println("Subtotal: $" + subtotal);
        System.out.println("Shipping Fees: $" + shippingFees);
        System.out.println("Total Paid: $" + total);
        System.out.println("Remaining Balance: $" + balance);
        System.out.println("------------------------------");

        cart.clear();
    }

}
