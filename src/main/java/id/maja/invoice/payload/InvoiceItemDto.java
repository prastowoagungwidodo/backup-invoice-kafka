package id.maja.invoice.payload;

import lombok.Value;

import java.io.Serializable;

@Value
public class InvoiceItemDto implements Serializable {
    String description;
    Integer qty;
    Double unitPrice;
    Double amount;
    Integer sequence;
}