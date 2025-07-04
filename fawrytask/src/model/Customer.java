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
                throw new IllegalStateException(product.getName() + " is expired");
            }

            if (item.getQuantity() > product.getQuantity()) {
                throw new IllegalStateException(product.getName() + " out of stock");
            }

            subtotal += item.getPrice();

            if (product.isShippable()) {
                shippingFees += 10.0 * item.getQuantity();
                shippableItems.add(new Shippable() {
                    public String getName() { return item.getQuantity() + "x " + product.getName(); }
                    public double getWeight() { return product.getWeight() * item.getQuantity(); }
                });
            }
        }

        double total = subtotal + shippingFees;

        if (balance < total) {
            throw new IllegalStateException("Insufficient balance. Needed: $" + total + ", Available: $" + balance);
        }

        for (CartItem item : cart) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }

        balance -= total;

        if (!shippableItems.isEmpty()) {
            ShippingService.ship(shippableItems);
        }

        System.out.println("** Checkout receipt **");
        for (CartItem item : cart) {
            System.out.println(item.getQuantity() + "x " + item.getProduct().getName() + " $" + (int)item.getPrice());
        }
        System.out.println("----------------------");
        System.out.println("Subtotal: $" + (int)subtotal);
        System.out.println("Shipping: $" + (int)shippingFees);
        System.out.println("Amount: $" + (int)total);
        System.out.println("Remaining Balance: $" + balance);
        cart.clear();
    }

}
