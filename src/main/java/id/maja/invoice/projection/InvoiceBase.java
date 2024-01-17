package id.maja.invoice.projection;

import java.time.OffsetDateTime;

public interface InvoiceBase {
    Long getId();
    String getNumber();
    OffsetDateTime getDate();
    Double getAmount();
    Double getRemainingAmount();
    Double getTotalPaidAmount();
    Boolean getPaid();
    String getMerchantCode();
    String getMerchantName();
    String getAggregatorCode();
    String getBranchCode();
    String getMerchantParentCode();
    String getName();
    String getEmail();
    String getPhone();
    VirtualAccountBase getVirtualAccount();
}
