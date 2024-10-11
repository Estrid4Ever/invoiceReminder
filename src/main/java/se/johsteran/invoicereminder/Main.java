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

        ArrayList<RowContent> allInvoicesContent = getAllInvoicesContent();
        System.out.println(allInvoicesContent);

        /*
        1. read invoice files (this year and last year)
        2. add eligible files info to list
        3. cross-reference with "db" file to not send duplicates
        4. generate email content
        5. send email(s)

        */

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
