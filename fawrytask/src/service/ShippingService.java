package service;

import java.util.List;

public class ShippingService {
    public static void ship(List<Shippable> items) {
        System.out.println("** Shipment notice **");
        double totalWeight = 0;

        for (Shippable item : items) {
            double weight = item.getWeight();
            totalWeight += weight;
            System.out.println(item.getName() + " " + weight + "kg");
        }

        System.out.printf("Total package weight %.1fkg\n", totalWeight);
        System.out.println();
    }
}
