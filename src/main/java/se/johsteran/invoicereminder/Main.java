package se.johsteran.invoicereminder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Main implements CommandLineRunner {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void run(String... args) throws Exception {


        System.out.println(getAllInvoicesContent());

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
        invoiceFileReader.setDirectory("/home/johannes/Documents/GitHub/invoiceReminder");
        invoiceFileReader.findXlsxFilesInDirectory();
        invoiceFileReader.readAllWorkBooks();

        return invoiceFileReader.getRows();
    }
}
