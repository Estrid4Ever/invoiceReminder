package se.johsteran.invoicereminder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class Main implements CommandLineRunner {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void run(String... args) throws Exception {
        //read all files from this year and last
        ArrayList<RowContent> rowContents = getAllInvoicesContent();

        //convert RowContent to InvoiceContent
        ArrayList<InvoiceContent> allInvoiceContents = rowContentToInvoiceContent(rowContents);

        //check for files eligible to send email (invoice date)
        ArrayList<InvoiceContent> eligibleInvoiceReminders = checkForEligibleInvoiceReminders(allInvoiceContents);

        //check for files eligible to send email (due date)
        ArrayList<InvoiceContent> eligibleDueDateReminders = checkForEligibleDueDateReminders(allInvoiceContents);

        System.out.println(eligibleInvoiceReminders);

        //update the records of what emails has been sent
        /*updateReferenceFile(eligibleInvoiceReminders, "sentInvoices.txt");
        updateReferenceFile(eligibleDueDateReminders, "sentPaymentDues.txt");*/

        //generate and send email
        /*sendInvoiceReminderEmails(eligibleInvoiceReminders);
        sendPaymentDueEmail(eligibleDueDateReminders);*/

        /*
        1. read invoice files (this year and last year)
        2. add eligible files info to list
        3. cross-reference with "db" file to not send duplicates
        4. update said "db" file
        5. generate email content
        6. send email(s)
        */

    }

    public void sendInvoiceReminderEmails(ArrayList<InvoiceContent> invoices) {
        ReminderEmail reminderEmail = new ReminderEmail(emailSender);

        for (InvoiceContent invoice : invoices) {
            reminderEmail.sendReminderEmail(invoice);
        }
    }

    public void sendPaymentDueEmail(ArrayList<InvoiceContent> invoices) {
        ReminderEmail reminderEmail = new ReminderEmail(emailSender);

        for (InvoiceContent invoice : invoices) {
            reminderEmail.sendPaymentIsDueEmail(invoice);
        }
    }

    public void updateReferenceFile(ArrayList<InvoiceContent> invoices, String fileName) {
        EligibleForDispatchHandler eligibleForDispatchHandler = new EligibleForDispatchHandler();

        for (InvoiceContent invoice : invoices) {
            eligibleForDispatchHandler.updateFile(invoice.getFileName(), fileName);
        }
    }

    public ArrayList<InvoiceContent> checkForEligibleInvoiceReminders(ArrayList<InvoiceContent> allInvoices) {
        EligibleForDispatchHandler eligibleForDispatchHandler = new EligibleForDispatchHandler();

        ArrayList<InvoiceContent> invoicesToSend = new ArrayList<>();

        for (InvoiceContent invoice : allInvoices) {
            if (eligibleForDispatchHandler.checkIfInvoiceReminderShouldBeSentForFile(invoice)) {
                invoicesToSend.add(invoice);
            }
        }

        return invoicesToSend;
    }

    public ArrayList<InvoiceContent> checkForEligibleDueDateReminders(ArrayList<InvoiceContent> allInvoices) {
        EligibleForDispatchHandler eligibleForDispatchHandler = new EligibleForDispatchHandler();

        ArrayList<InvoiceContent> invoicesToSend = new ArrayList<>();

        for (InvoiceContent invoice : allInvoices) {
            if (eligibleForDispatchHandler.checkIfPaymentDueReminderShouldBeSentForFile(invoice)) {
                invoicesToSend.add(invoice);
            }
        }

        return invoicesToSend;
    }

    public ArrayList<InvoiceContent> rowContentToInvoiceContent (ArrayList<RowContent> allRowContents) {
        ArrayList<InvoiceContent> invoices = new ArrayList<>();

        for (RowContent row : allRowContents) {

            invoices.add(new InvoiceContent(row.getCellContents().get(0),
                    row.getCellContents().get(1),
                    row.getCellContents().get(2),
                    row.getCellContents().get(3)));
        }

        return invoices;
    }

    public ArrayList<RowContent> getAllInvoicesContent() {

        InvoiceFileReader invoiceFileReader = new InvoiceFileReader();
        invoiceFileReader.addCellsToRead("F22");
        invoiceFileReader.addCellsToRead("F5");
        invoiceFileReader.addCellsToRead("F6");

        /*for dev*/
        invoiceFileReader.setDirectory("/home/johannes/Documents/GitHub/invoiceReminder");
        invoiceFileReader.findXlsxFilesInDirectory();
        invoiceFileReader.readAllWorkBooks();

        /*for deployment*/
        /*for (int i = 0; i < 2; i++) {
            String dir =System.getenv("invoiceDirectory") + "Fakturor " + (LocalDate.now().getYear()-i);
            invoiceFileReader.setDirectory(dir);
            invoiceFileReader.findXlsxFilesInDirectory();
            invoiceFileReader.readAllWorkBooks();
        }*/

        return invoiceFileReader.getRows();
    }
}
