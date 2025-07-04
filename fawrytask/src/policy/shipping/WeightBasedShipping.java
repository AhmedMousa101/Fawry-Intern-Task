package policy.shipping;

public class WeightBasedShipping implements ShippingPolicy{
    private double weight;

    public WeightBasedShipping(double weight) {
        this.weight = weight;
    }
    @Override
    public boolean requiresShipping() {
        return true;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }
}
