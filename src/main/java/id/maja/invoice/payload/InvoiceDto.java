package id.maja.invoice.payload;

import lombok.Value;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;

@Value
public class InvoiceDto implements Serializable {
    String number;
    OffsetDateTime date;
    OffsetDateTime dueDate;
    Double amount;
    String merchantCode;
    String name;
    String email;
    String phone;
    String address;
    Set<InvoiceItemDto> items;
    VirtualAccountDto virtualAccount;
    String createdBy;
}