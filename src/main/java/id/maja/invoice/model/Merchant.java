package id.maja.invoice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize
public class Merchant {
    private String code;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String logo;
    private String type;
    private String secret;
    private String apiVersion;
    private String parent;
    private Integer fee;
    private String feeType;
    private String webhookUrl;
    private String createDate;
    private String updateDate;
    private String deleteDate;
    private Integer version;
    private boolean invoiceEmailNotification;
    private boolean invoiceWhatsAppNotification;
    private boolean invoiceSmsNotification;
    private boolean paymentEmailNotification;
    private boolean paymentWhatsAppNotification;
    private boolean paymentSmsNotification;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private String h2hInquiryURL;
    private String h2hPaymentURL;
    private String h2hReversalURL;
    private String h2hSecretKey;
}
