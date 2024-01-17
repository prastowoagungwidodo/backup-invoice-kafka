package id.maja.invoice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "invoice_item")
public class InvoiceItem {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "qty", nullable = false)
    private Integer qty = 1;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice = 0.0;

    @Column(name = "amount", nullable = false)
    private Double amount = 0.0;

    @Column(name = "sequence", nullable = false)
    private Integer sequence = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    private Invoice invoice;
}