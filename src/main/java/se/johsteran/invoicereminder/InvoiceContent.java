package se.johsteran.invoicereminder;

import java.time.LocalDate;

public class InvoiceContent {
    private LocalDate dueDate;
    private String fileName;
    private String recipient;

    public InvoiceContent(LocalDate dueDate, String fileName, String recipient) {
        this.dueDate = dueDate;
        this.fileName = fileName;
        this.recipient = recipient;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
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
                "dueDate=" + dueDate +
                ", fileName='" + fileName + '\'' +
                ", recipient='" + recipient + '\'' +
                '}';
    }
}
