# invoiceReminder
This is a program that sends an email reminder to the sender of the invoice when its time to send it. 
The application is designed to run once a day (through task scheduler or similar). 
Once running, a pre-selected folder containing the invoices is checked and if the requirements are met an email is sent to the user (NOT directly to the customer). 
A database is needed to keep track of wich reminder has been sent.
