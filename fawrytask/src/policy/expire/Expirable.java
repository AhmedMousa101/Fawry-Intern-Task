package policy.expire;

import java.time.LocalDate;

public class Expirable implements ExpirePolicy {
    private LocalDate expirationDate;
    public Expirable(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
    @Override
    public boolean isExpired() {
        return expirationDate.isBefore(LocalDate.now());
    }
}
