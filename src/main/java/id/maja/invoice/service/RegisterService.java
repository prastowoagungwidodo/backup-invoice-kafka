package id.maja.invoice.service;

import id.maja.invoice.entity.Invoice;
import id.maja.invoice.entity.VirtualAccount;
import id.maja.invoice.model.Merchant;
import id.maja.invoice.payload.InvoiceDto;
import id.maja.invoice.projection.InvoiceBase;
import id.maja.invoice.repository.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    Logger logger = LoggerFactory.getLogger(RegisterService.class);
    private InvoiceRepository invoiceRepository;
    private MerchantService merchantService;

    RegisterService(InvoiceRepository invoiceRepository){
        this.invoiceRepository = invoiceRepository;
    }

    @Autowired
    public void setMerchantService(MerchantService merchantService){
        this.merchantService = merchantService;
    }

    public Invoice register(InvoiceDto data) throws Exception {
        this.logger.debug("Register invoice");
        this.logger.debug("data: {}", data);
        Merchant merchant = this.merchantService.findByCode(data.getMerchantCode());

        if (merchant == null) {
            throw new Exception("Merchant not found");
        }

        Invoice invoice = getInvoice(data, merchant);

        VirtualAccount virtualAccount = new VirtualAccount();
        virtualAccount.setActive(true);
        virtualAccount.setAggregatorCode(merchant.getParent());
        virtualAccount.setNumber(data.getVirtualAccount().getNumber());
        virtualAccount.setMerchantCode(data.getMerchantCode());
        virtualAccount.setMerchantName(merchant.getName());
        virtualAccount.setAggregatorCode(merchant.getParent());
        virtualAccount.setExpiredDate(data.getVirtualAccount().getExpiredDate());
        virtualAccount.setMaximumPaymentAmount(data.getVirtualAccount().getMaximumPaymentAmount());
        virtualAccount.setMinimumPaymentAmount(data.getVirtualAccount().getMinimumPaymentAmount());
        virtualAccount.setType(data.getVirtualAccount().getType());


        return invoice;
    }

    private static Invoice getInvoice(InvoiceDto data, Merchant merchant) {
        Invoice invoice = new Invoice();
        invoice.setAddress(data.getAddress());
        invoice.setAmount(data.getAmount());
        invoice.setAggregatorCode(merchant.getParent());
        invoice.setCanceled(false);
        invoice.setCreatedBy(data.getCreatedBy());
        invoice.setDate(data.getDate());
        invoice.setDueDate(data.getDueDate());
        invoice.setEmail(data.getEmail());
        invoice.setMerchantCode(data.getMerchantCode());
        invoice.setMerchantName(merchant.getName());
        invoice.setName(data.getName());
        invoice.setNumber(data.getNumber());
        invoice.setPhone(data.getPhone());
        return invoice;
    }
}
