package id.maja.invoice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "virtual_account")
public class VirtualAccount {
    @Id()
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "type", nullable = false)
    private String type = "close";

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "active_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime activeDate;

    @Column(name = "expired_date")
    private OffsetDateTime expiredDate;

    @Column(name = "min_payment_amount")
    private Double minimumPaymentAmount;

    @Column(name = "max_payment_amount")
    private Double maximumPaymentAmount;

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

    @OneToMany(mappedBy = "virtualAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Invoice> invoices;
}
