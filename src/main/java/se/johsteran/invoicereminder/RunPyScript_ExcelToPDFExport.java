package se.johsteran.invoicereminder;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RunPyScript_ExcelToPDFExport {

    public void export() {
        String excelFilePath = "C:\\path\\to\\input.xlsx";
        String outputDir = "C:\\path\\to\\output\\";  // Output directory

        // LibreOffice command to convert Excel to PDF
        String[] command = {
                "soffice", "--headless", "--convert-to", "pdf", excelFilePath, "--outdir", outputDir
        };

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            // Capture the output from the LibreOffice command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("LibreOffice exited with code: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void runPyScript(String filename) {
//        // Paths for the Excel file and output PDF file
//        String excelFilePath = "1 MALL 2024 Faktura till kattägare (1 katt).xlsx";
//        String pdfFilePath = "1 MALL 2024 Faktura till kattägare (1 katt).pdf";
//
//        // Command to run Python script
//        ArrayList<String> command = new ArrayList<>();
//        command.add("python");  // Assuming "python" is in your system's PATH
//        command.add("src/main/python/excelToPDFExport.py");  // Path to the Python script
//        command.add(excelFilePath);  // First argument to the Python script (Excel file path)
//        command.add(pdfFilePath);  // Second argument to the Python script (PDF file path)
//
//        try {
//            // Create the process builder
//            ProcessBuilder processBuilder = new ProcessBuilder(command);
//            processBuilder.redirectErrorStream(true);  // Merge stderr with stdout
//
//            // Start the process
//            Process process = processBuilder.start();
//
//            // Capture and print the output of the Python script
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            // Wait for the process to complete and get the exit code
//            int exitCode = process.waitFor();
//            System.out.println("Python script exited with code: " + exitCode);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
