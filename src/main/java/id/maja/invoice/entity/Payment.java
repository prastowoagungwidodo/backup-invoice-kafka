package id.maja.invoice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {
    @Id()
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime date;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "ref", nullable = false)
    private String ref;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "channel", nullable = false)
    private String channel;

    @Column(name = "terminal")
    private String terminal;

    @Column(name = "credit", nullable = false)
    private Boolean credit = true;

    @Column(name = "debit", nullable = false)
    private Boolean debit = false;

    @Column(name = "posted", nullable = false)
    private Boolean posted = false;

    @Column(name = "posted_date")
    private OffsetDateTime postedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private VirtualAccount virtualAccount;

    @Column(name = "company_code", nullable = false, length = 8)
    private String companyCode = "BSI";

    @Column(name = "branch_code", length = 8)
    private String branchCode;

    @Column(name = "aggregator_code", length = 8)
    private String aggregatorCode;

    @Column(name = "merchant_parent_code", length = 8)
    private String merchantParentCode;

    @Column(name = "merchant_code", nullable = false, length = 8)
    private String merchantCode;

    @Column(name = "merchant_name", nullable = false, length = 64)
    private String merchantName;
}
