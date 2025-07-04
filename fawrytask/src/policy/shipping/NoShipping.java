package policy.shipping;

public class NoShipping implements ShippingPolicy{
    @Override
    public boolean requiresShipping() {
        return false;
    }

    @Override
    public double getWeight(){
        return 0;
    }
}
