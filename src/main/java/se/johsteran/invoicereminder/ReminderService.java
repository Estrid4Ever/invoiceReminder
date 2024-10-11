package se.johsteran.invoicereminder;

import java.time.LocalDate;
import java.util.Objects;

public class ReminderService {

    public ReminderService() {
    }

    public boolean checkIfReminderShouldBeSentForFile(InvoiceContent invoiceContent) {

        if (Objects.equals(invoiceContent.getInvoiceDate(), LocalDate.now())) {
            return true;
        } else {
            return false;
        }
    }

}
