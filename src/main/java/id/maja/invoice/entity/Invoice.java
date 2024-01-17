package id.maja.invoice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "region", nullable = false)
    private Integer region;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime date;

    @Column(name = "due_date")
    private OffsetDateTime dueDate;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency = "IDR";

    @Column(name = "amount", nullable = false)
    private Double amount = 0.0;

    @Column(name = "remaining_amount", nullable = false)
    private Double remainingAmount = 0.0;

    @Column(name = "total_paid_amount", nullable = false)
    private Double totalPaidAmount = 0.0;

    @Column(name = "paid", nullable = false)
    private Boolean paid = false;

    @Column(name = "paid_date")
    private OffsetDateTime paidDate;

    @Column(name = "credit", nullable = false)
    private Boolean credit = false;

    @Column(name = "debit", nullable = false)
    private Boolean debit = true;

    @Column(name = "posted", nullable = false)
    private Boolean posted = false;

    @Column(name = "posted_date")
    private OffsetDateTime postedDate;

    @Column(name = "canceled", nullable = false)
    private Boolean canceled = false;

    @Column(name = "cancelation_notes")
    private String cancelationNotes;

    @Column(name = "cancelation_date")
    private OffsetDateTime cancelationDate;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "revision", nullable = false)
    private Integer revision = 0;

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

    @Column(name = "name", length = 64)
    private String name;

    @Column(name = "email", length = 64)
    private String email;

    @Column(name = "phone", length = 16)
    private String phone;

    @Column(name = "address", length = 128)
    private String address;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<InvoiceItem> items;

    @ManyToOne(fetch = FetchType.EAGER)
    private VirtualAccount virtualAccount;
}