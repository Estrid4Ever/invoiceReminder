package se.johsteran.invoicereminder;

import java.time.LocalDate;

public class InvoiceContent {
    private LocalDate invoiceDate;
    private String fileName;
    private String recipient;

    public InvoiceContent(LocalDate invoiceDate, String fileName, String recipient) {
        this.invoiceDate = invoiceDate;
        this.fileName = fileName;
        this.recipient = recipient;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return "InvoiceContent{" +
                "dueDate=" + invoiceDate +
                ", fileName='" + fileName + '\'' +
                ", recipient='" + recipient + '\'' +
                '}';
    }
}
