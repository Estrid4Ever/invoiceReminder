package se.johsteran.invoicereminder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.ToDoubleBiFunction;

@Component
public class Main implements CommandLineRunner {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void run(String... args) throws Exception {

        InvoiceFileReader invoiceFileReader = new InvoiceFileReader();
        invoiceFileReader.setDirectory("/home/johannes/Programmering/Github/invoiceReminder");
        invoiceFileReader.addCellsToRead("F6");
        invoiceFileReader.findXlsxFilesInDirectory();
        invoiceFileReader.readAllWorkBooks();

        System.out.println("result: " + invoiceFileReader.getRows());

        // 1. read files
        // 2. add eligible files info to list
        // 3. cross reference with db(or in file checkmark?) to not send duplicate
        // 4. generate email content
        // 5. send email
    }
}
