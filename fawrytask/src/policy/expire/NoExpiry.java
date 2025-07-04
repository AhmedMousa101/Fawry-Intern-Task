package policy.expire;

public class NoExpiry implements ExpirePolicy {
    @Override
    public boolean isExpired(){
        return false;
    }
}
