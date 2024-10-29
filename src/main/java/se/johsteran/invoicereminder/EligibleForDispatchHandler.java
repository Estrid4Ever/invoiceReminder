package se.johsteran.invoicereminder;

import java.io.*;
import java.time.LocalDate;

public class EligibleForDispatchHandler {

    private String sentInvoicesFileContent;
    private String sentPaymentDuesFileContent;

    public boolean checkIfInvoiceReminderShouldBeSentForFile(InvoiceContent invoiceContent) {

        setSentInvoicesFileContent(readFile("sentInvoices.txt"));

        if (getSentInvoicesFileContent().contains(invoiceContent.getFileName())) {
            return false;
        }

        LocalDate invoiceDate = null;
        try {
            char[] dateArray = invoiceContent.getInvoiceDate().toCharArray();
            invoiceDate = LocalDate.parse("20" + dateArray[0] + dateArray[1] + "-" + dateArray[2] + dateArray[3] + "-" + dateArray[4] + dateArray[5]);
        } catch (Exception e) {
            return false;
        }

        if (invoiceDate.isBefore(LocalDate.now()) && invoiceDate.isAfter(LocalDate.now().minusDays(2))) {
            return false;
        }

        return true;
    }

    public boolean checkIfPaymentDueReminderShouldBeSentForFile(InvoiceContent invoiceContent) {

        setSentPaymentDuesFileContent(readFile("sentPaymentDues.txt"));

        if(getSentPaymentDuesFileContent().contains(invoiceContent.getFileName())) {
            return false;
        }

        LocalDate paymentDueDate = null;
        try {
            char[] dateArray = invoiceContent.getPaymentDueDate().toCharArray();
            paymentDueDate = LocalDate.parse("20" + dateArray[0] + dateArray[1] + "-" + dateArray[2] + dateArray[3] + "-" + dateArray[4] + dateArray[5]);
        } catch (Exception e) {
            return false;
        };

        if (paymentDueDate.isEqual(LocalDate.now()) || paymentDueDate.isEqual(LocalDate.now().plusDays(1))) {
            return true;
        }

        return false;
    }

    public String readFile(String fileName) {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            return sb.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateFile(String invoiceFileNameToAdd, String fileName) {
        try {
            FileWriter myWriter = new FileWriter(fileName, true);
            myWriter.write(invoiceFileNameToAdd + "\n");
            myWriter.flush();
            myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred." + e);
        }
    }

    public String getSentInvoicesFileContent() {
        return sentInvoicesFileContent;
    }

    public void setSentInvoicesFileContent(String sentInvoicesFileContent) {
        this.sentInvoicesFileContent = sentInvoicesFileContent;
    }

    public String getSentPaymentDuesFileContent() {
        return sentPaymentDuesFileContent;
    }

    public void setSentPaymentDuesFileContent(String sentPaymentDuesFileContent) {
        this.sentPaymentDuesFileContent = sentPaymentDuesFileContent;
    }
}
