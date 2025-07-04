import model.Customer;
import model.Product;
import policy.expire.Expirable;
import policy.expire.NoExpiry;
import policy.shipping.NoShipping;
import policy.shipping.WeightBasedShipping;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Test1_NormalCheckout();
        Test2_InsufficientBalance();
        Test3_EmptyCart();
        Test4_OutOfStock();
        Test5_ExpiredProduct();
    }

    static void Test1_NormalCheckout() {
        System.out.println("=== Test Case 1: Normal Checkout ===");
        Customer customer = new Customer("Ahmed", 500);

        Product cheese = new Product("Cheese", 50, 5,
                new Expirable(LocalDate.now().plusDays(2)),
                new WeightBasedShipping(2.0));

        Product tv = new Product("TV", 300, 2,
                new NoExpiry(),
                new WeightBasedShipping(10.0));

        Product card = new Product("Scratch Card", 30, 10,
                new NoExpiry(),
                new NoShipping());

        customer.addToCart(cheese, 2);
        customer.addToCart(tv, 1);
        customer.addToCart(card, 1);

        customer.checkout();
    }

    static void Test2_InsufficientBalance() {
        System.out.println("\n=== Test Case 2: Insufficient Balance ===");
        try {
            Customer customer = new Customer("Youssef", 100);
            Product tv = new Product("TV", 300, 2,
                    new NoExpiry(),
                    new WeightBasedShipping(10.0));

            customer.addToCart(tv, 1);
            customer.checkout();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void Test3_EmptyCart() {
        System.out.println("\n=== Test Case 3: Empty Cart ===");
        try {
            Customer customer = new Customer("Marwan", 200);
            customer.checkout();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void Test4_OutOfStock() {
        System.out.println("\n=== Test Case 4: Out of Stock ===");
        try {
            Customer customer = new Customer("MHMD", 1000);
            Product cheese = new Product("Cheese", 50, 3,
                    new Expirable(LocalDate.now().plusDays(2)),
                    new WeightBasedShipping(1.0));

            customer.addToCart(cheese, 5);
            customer.checkout();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void Test5_ExpiredProduct() {
        System.out.println("\n=== Test Case 5: Expired Product ===");
        try {
            Customer customer = new Customer("Nour", 500);
            Product expiredBiscuits = new Product("Biscuits", 20, 5,
                    new Expirable(LocalDate.now().minusDays(1)),
                    new WeightBasedShipping(0.5));

            customer.addToCart(expiredBiscuits, 1);
            customer.checkout();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}
