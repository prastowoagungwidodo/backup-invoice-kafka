package id.maja.invoice.projection;

import java.time.OffsetDateTime;

public interface VirtualAccountBase {
    Long getId();
    String getNumber();
    String getType();
    Boolean getActive();
    OffsetDateTime getActiveDate();
    OffsetDateTime getExpiredDate();
    Double getMinimumPaymentAmount();
    Double getMaximumPaymentAmount();
}
