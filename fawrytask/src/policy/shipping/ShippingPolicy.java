package policy.shipping;

public interface ShippingPolicy {
    boolean requiresShipping();
    double getWeight();
}
