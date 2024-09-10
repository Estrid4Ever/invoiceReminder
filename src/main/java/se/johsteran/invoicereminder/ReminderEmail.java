package se.johsteran.invoicereminder;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class ReminderEmail {
    private static final String NEW_USER_ACCOUNT_VERIFICATION = "Headline";
    private static final String NEW_ORDER_VERIFICATION = "Hej Sara!! denna e från mitt program";

    public JavaMailSender emailSender;

    public ReminderEmail(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    public void sendVerificationToken(String to, String token) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setTo(to);
            message.setText("wazzaaah: " + token);
            emailSender.send(message);
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }


    public void sendOrderVerification(String to) {
        String orderText = generateOrderHtmlEmail();
        try {

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setSubject(NEW_ORDER_VERIFICATION);
            helper.setFrom("lohagakattpensionat@gmail.com");
            helper.setTo(to);
            helper.setText(orderText, true); // true indikerar att innehållet är HTML
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error while sending email: " + e.getMessage(), e);
        }
    }

    public String generateOrderHtmlEmail(){
        StringBuilder emailContent = new StringBuilder();
        int totalOrderPrice = 0;

        // Börja HTML-dokumentet
        emailContent.append("<html><body>");

        // Lägg till rubrik för beställningen
        emailContent.append("<h2>Din beställning:</h2>");

        // Skapa en tabell för att visa produkterna
        emailContent.append("<table border=\"1\">");
        emailContent.append("<tr><th>Produkt</th><th>Antal</th><th>Pris</th></tr>");

//        Enumeration<Products> keys = items.keys();
//        while (keys.hasMoreElements()) {
//            Products key = keys.nextElement();
//            Integer value = items.get(key);
//            int totalPrice = key.getPrice() * value;
//
//            // Lägg till varje produkt i tabellen
//            emailContent.append("<tr>");
//            emailContent.append("<td>").append(key.getName()).append("</td>");
//            emailContent.append("<td>").append(value).append("</td>");
//            emailContent.append("<td>").append(totalPrice).append(" kr</td>");
//            emailContent.append("</tr>");
//            totalOrderPrice += totalPrice;
//
//        }

        // Avsluta tabellen och HTML-dokumentet
        emailContent.append("</table>");
        emailContent.append("<p> Total pris: </p>" + totalOrderPrice);
        emailContent.append("<p>Tack för din beställning!</p>");
        emailContent.append("</body></html>");


        return emailContent.toString();
    }

}
