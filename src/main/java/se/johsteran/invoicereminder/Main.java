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
        ReminderEmail reminderEmail = new ReminderEmail(emailSender);

//        reminderEmail.sendVerificationToken("johannes-sr@hotmail.com", "hejhej");
        reminderEmail.sendOrderVerification("sarawkupari@gmail.com");
    }
}
