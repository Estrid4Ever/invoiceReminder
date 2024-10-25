package se.johsteran.invoicereminder;

import java.time.LocalDate;

public class InvoiceContent {
    private String fileName;
    private String totalPaymentSum;
    private String invoiceDate;
    private String paymentDueDate;

    public InvoiceContent(String fileName, String totalPaymentSum, String invoiceDate, String paymentDueDate) {
        this.fileName = fileName;
        this.totalPaymentSum = totalPaymentSum;
        this.invoiceDate = invoiceDate;
        this.paymentDueDate = paymentDueDate;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(String paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTotalPaymentSum() {
        return totalPaymentSum;
    }

    public void setTotalPaymentSum(String totalPaymentSum) {
        this.totalPaymentSum = totalPaymentSum;
    }

    @Override
    public String toString() {
        return "InvoiceContent{" +
                "invoiceDate='" + invoiceDate + '\'' +
                ", paymentDueDate='" + paymentDueDate + '\'' +
                ", fileName='" + fileName + '\'' +
                ", totalPaymentSum='" + totalPaymentSum + '\'' +
                '}';
    }
}
