package id.maja.invoice.payload;

import lombok.Value;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link id.maja.invoice.entity.VirtualAccount}
 */
@Value
public class VirtualAccountDto implements Serializable {
    String number;
    String type;
    OffsetDateTime activeDate;
    OffsetDateTime expiredDate;
    Double minimumPaymentAmount;
    Double maximumPaymentAmount;
    String merchantCode;
}