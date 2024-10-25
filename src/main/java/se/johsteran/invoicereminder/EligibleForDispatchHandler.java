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

        LocalDate invoiceDate = LocalDate.parse(invoiceContent.getInvoiceDate());

        if (invoiceDate.isBefore(LocalDate.now()) || invoiceDate.isAfter(LocalDate.now().plusDays(5))) {
            return false;
        }

        return true;
    }

    public boolean checkIfPaymentDueReminderShouldBeSentForFile(InvoiceContent invoiceContent) {

        setSentPaymentDuesFileContent(readFile("sentPaymentDues.txt"));

        if(getSentPaymentDuesFileContent().contains(invoiceContent.getFileName())) {
            return false;
        }

        LocalDate invoiceDate = LocalDate.parse(invoiceContent.getPaymentDueDate());

        if (invoiceDate.isBefore(LocalDate.now()) || invoiceDate.isAfter(LocalDate.now().plusDays(5))) {
            return false;
        }

        return true;
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
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(invoiceFileNameToAdd);
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
