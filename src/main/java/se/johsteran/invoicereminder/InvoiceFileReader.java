package se.johsteran.invoicereminder;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class InvoiceFileReader {
    private ArrayList<RowContent> rows;
    private String directory;
    private ArrayList<String> files;
    private ArrayList<NumberTranslatedCellId> cellsToRead;

    public InvoiceFileReader() {
        this.rows = new ArrayList<>();
        this.files = new ArrayList<>();
        this.cellsToRead = new ArrayList<>();
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public ArrayList<RowContent> getRows() {
        return rows;
    }

    public ArrayList<String> getFiles() {
        return files;
    }

    public void addCellsToRead(String cell) {
        String[] firstAndSecondNr = cell.split("((?=\\d))", 2);

        int firstNumber = lettersToNumberConversion(firstAndSecondNr[0]);
        if (firstNumber == 100) {
            return;
        }

        int secondNumber = 0;
        try {
            secondNumber = Integer.parseInt(firstAndSecondNr[1]) -1;
        } catch (NumberFormatException e) {
            System.out.println(e);
        }


        cellsToRead.add(new NumberTranslatedCellId(firstNumber, secondNumber));
    }

    public int lettersToNumberConversion(String letters) {
        // Convert the column string to uppercase to handle lowercase inputs
        letters = letters.toUpperCase();
        int length = letters.length();
        int result = 0;

        // Iterate over each character in the string
        for (int i = 0; i < length; i++) {
            char c = letters.charAt(i);
            int value = c - 'A' + 1; // Calculate the value of the character
            result = result * 26 + value; // Update the result with the new value
        }

        return result - 1; //minus one to start the count from 0 instead of 1
    }

    public void findXlsxFilesInDirectory() {
        File dir = new File(directory);
        File[] dirFiles = dir.listFiles();

        if (dirFiles != null) {
            for (int i = 0; i < dirFiles.length; i++) {
                if(dirFiles[i].isFile() && dirFiles[i].getName().endsWith(".xlsx")) {
                    files.add(dirFiles[i].getAbsolutePath());
                }
            }
        }
    }

    public void readAllWorkBooks() {
        for (String file : files) {
            readWorkBook(file);
        }
    }

    public void readWorkBook(String file) {
        RowContent rowContent = new RowContent();
        rowContent.addCell(getFileNameFromAbsolutePath(file));
        cellsToRead.forEach((numberTranslatedCellId) -> {
            rowContent.addCell(readCellData(numberTranslatedCellId.column(), numberTranslatedCellId.row(), file));
        });

        rows.add(rowContent);
    }
    public String getFileNameFromAbsolutePath(String absolutePath) {
        String[] fileNames = absolutePath.split("[\\\\/]");
        return fileNames[fileNames.length - 1];
    }

    public String readCellData(int vColumn, int vRow, String file) {
        Workbook wb = null; //initialize Workbook null

        try {
            //reading data from a file in the form of bytes
            FileInputStream fis = new FileInputStream(file);
            //constructs an XSSFWorkbook object, by buffering the whole stream into the memory
            wb = new XSSFWorkbook(fis);
        }
        catch(IOException e1) {
            System.out.println(e1);
        }

        Sheet sheet = wb.getSheetAt(0); //getting the XSSFSheet object at given index
        if (sheet == null) {
            return "sheet N/A";
        }

        Row row = sheet.getRow(vRow); //returns the logical row
        if (row == null) {
            return "row N/A";
        }

        Cell cell = row.getCell(vColumn); //getting the cell representing the given column
        if (cell == null) {
            return "cell N/A";
        }

        String value = cell.getStringCellValue();   //gets the cell value

        return value == null ? "value N/A" : value;  //returns the cell value if it´s not null
    }
}
