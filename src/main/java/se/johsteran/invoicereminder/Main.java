package se.johsteran.invoicereminder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Main implements CommandLineRunner {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void run(String... args) throws Exception {

//        InvoiceFileReader invoiceFileReader = new InvoiceFileReader();
//        invoiceFileReader.setDirectory("/home/johannes/Documents/GitHub/invoiceReminder");
//        invoiceFileReader.addCellsToRead("F6");
//        invoiceFileReader.findXlsxFilesInDirectory();
//        invoiceFileReader.readAllWorkBooks();
//
//
//        ExcelToPDFExport run = new ExcelToPDFExport();
//        run.exportExcelToPDFUsingITextAndPoi("/home/johannes/Documents/GitHub/invoiceReminder/1 MALL 2024 Faktura till kattägare (1 katt).xlsx");


        // 1. read files
        // 2. add eligible files info to list
        // 3. cross-reference with db(or in file checkmark?) to not send duplicate
        // 4. generate email content
        // 5. send email
    }
}
