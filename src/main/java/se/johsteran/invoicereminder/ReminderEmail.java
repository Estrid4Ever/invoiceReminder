package se.johsteran.invoicereminder;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class ReminderEmail {

    public JavaMailSender emailSender;
    private String[] emails = {"johannes.randen@gmail.com"};

    public ReminderEmail(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    public String getCatName(String fileName) {
        String[] catName = fileName.split("-0\\d");

        return catName.length > 1 ? catName[1].split("\\.")[0].trim() : fileName;
    }

    public void sendReminderEmail(InvoiceContent invoice) {
        String orderText = generateInvoiceReminderHtmlEmail(invoice);

        String catName = getCatName(invoice.getFileName());

        try {

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setSubject("Påminnelse om fakturautskick: " + catName);
            helper.setFrom("lohagakattpensionat@gmail.com");
            helper.setTo(emails);
            helper.setText(orderText, true); // true indikerar att innehållet är HTML
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error while sending email: " + e.getMessage(), e);
        }
    }

    public String generateInvoiceReminderHtmlEmail(InvoiceContent invoice){

        String invoiceReminderTemplate = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Påminnelse om fakturautskick</title>
                </head>
                <style>
                
                    body {
                        background-color: #0e0e0e;
                        color: #ffffff;
                        font-family: "DejaVu Sans Mono",monospace ;
                        padding: 1rem;
                    }
                    li {
                        margin-bottom: 30px;
                    }
                
                    h4 {
                        font-size: large;
                    }
                
                    p {
                        margin-bottom: 5rem;
                    }
                
                    div {
                        display: flex;
                        align-items: center;
                        justify-content: center;
                
                    }
                
                    ul {
                        width: fit-content;
                        padding: 2rem;
                        border-radius: 1rem;
                        background-color: rgb(30, 47, 69);
                
                        /*color: aliceblue;*/
                        scale: 1.4;
                    }
                
                    span {
                        text-decoration: underline;
                        font-size: xx-large;
                        font-weight: bold;
                    }
                
                </style>
                <body>
                <h2>Det är dags att <span>skicka faktura</span> för: %s</h2>
                <hr>
                <p>Detta är en automatisk påminnelse om fakturautskick.</p>
                <div>
                <ul>
                    <li>Katt(er): <h4>%s</h4></li>
                    <li>Total summa: <h4>%s kr</h4></li>
                </ul>
                </div>
                </body>
                </html>""";

        String catName = getCatName(invoice.getFileName());

        String invoiceReminderEmail = String.format(invoiceReminderTemplate, catName, catName, invoice.getTotalPaymentSum());

        return invoiceReminderEmail;
    }

    public void sendPaymentIsDueEmail(InvoiceContent invoice) {
        String orderText = generatePaymentIsDueHtmlEmail(invoice);

        String catName = getCatName(invoice.getFileName());

        try {

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setSubject("Förfallodatum för: " + catName);
            helper.setFrom("lohagakattpensionat@gmail.com");
            helper.setTo(emails);
            helper.setText(orderText, true); // true indikerar att innehållet är HTML
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error while sending email: " + e.getMessage(), e);
        }
    }

    private String generatePaymentIsDueHtmlEmail(InvoiceContent invoice) {
        String invoiceReminderTemplate = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Påminnelse för fakturautskick</title>
                </head>
                <style>
                
                    body {
                        background-color: #0e0e0e;
                        color: #ffffff;
                        font-family: "DejaVu Sans Mono",monospace ;
                        padding: 1rem;
                    }
                    li {
                        margin-bottom: 30px;
                    }
                
                    h4 {
                        font-size: large;
                    }
                
                    p {
                        margin-bottom: 5rem;
                    }
                
                    div {
                        display: flex;
                        align-items: center;
                        justify-content: center;
                
                    }
                
                    ul {
                        width: fit-content;
                        padding: 2rem;
                        border-radius: 1rem;
                        background-color: rgb(69, 38, 30);
                
                        /*color: aliceblue;*/
                        scale: 1.4;
                    }
                
                    span {
                        text-decoration: underline;
                        font-size: xx-large;
                        font-weight: bold;
                    }
                
                </style>
                <body>
                <h2>Nu ska fakturan för: %s, <span>vara betald</span></h2>
                <hr>
                <p>Detta är en automatisk påminnelse om förfallodatum.</p>
                <div>
                <ul>
                    <li>Katt(er): <h4>%s</h4></li>
                    <li>Total summa: <h4>%s</h4></li>
                </ul>
                </div>
                </body>
                </html>""";

        String catName = getCatName(invoice.getFileName());

        String invoiceReminderEmail = String.format(invoiceReminderTemplate, catName, catName, invoice.getTotalPaymentSum());

        return invoiceReminderEmail;
    }

}
