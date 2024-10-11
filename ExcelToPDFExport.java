package se.johsteran.invoicereminder;


import com.itextpdf.text.Document;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.io.*;

public class ExcelToPDFExport {

    public void exportExcelToPDFUsingITextAndPoi(String excelFilePath) {

        String pdfFilePath = excelFilePath.replace(".xlsx", ".pdf");    // Output PDF file path
        System.out.println(pdfFilePath);

//         Read the Excel file and create a new PDF file
        try (FileInputStream excelFile = new FileInputStream(new File(excelFilePath))) {
            // Load the Excel workbook
            Workbook workbook = new XSSFWorkbook(excelFile);

            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();  // Create the evaluator


            // Create a new PDF document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath)); // Create a NEW PDF file
            document.open();

            // Loop through each sheet in the workbook
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);

                int maxColumns = findMaxColumn(sheet);
                PdfPTable table = new PdfPTable(maxColumns-1);

                // Loop through each row in the sheet
                for (int j = 0; j < sheet.getLastRowNum(); j++) {
                    Row row = sheet.getRow(j);
                    // Loop through each cell in the row
                    int x = 1;
                    for (int k = 1; k < maxColumns; k++) {
                        if (k == 2) {

                            continue;
                        }

                        PdfPCell cellPdf = new PdfPCell();

                        if (k == 1) {
                            cellPdf.setColspan(2);
                        }

                        if (row == null) {
                            cellPdf.setPhrase(new Phrase(" "));
                        } else {


                            Cell cell = row.getCell(k);

                            // Get the cell value as a string
                            String cellValue = getCellValue(cell, evaluator);

                            cellPdf.setPhrase(new Phrase(cellValue, getCellStyle(cell)));
                            setBackgroundColor(cell, cellPdf);
                            setCellAlignment(cell, cellPdf);
                        }

                        /*cellPdf.setBorder(0);*/
                        // Add the cell value to the PDF table
                        table.addCell(cellPdf);

                    }
                }

                // Add the table to the PDF document
                document.add(table);
                document.newPage();  // Create a new page for each sheet
            }

            document.close(); // Close the PDF document
            System.out.println("PDF created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int findMaxColumn(Sheet sheet) {
        int maxColumns = 0;
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (row.getLastCellNum() > maxColumns) {
                    maxColumns = row.getLastCellNum();
                }
            }
        }
        System.out.println(maxColumns);
        return maxColumns;
    }

    private void setCellAlignment(Cell cell, PdfPCell cellPdf) {
        if (cell == null) {
            return;
        }

        CellStyle cellStyle = cell.getCellStyle();

        HorizontalAlignment horizontalAlignment = cellStyle.getAlignment();

        switch (horizontalAlignment) {
            case LEFT:
                cellPdf.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case CENTER:
                cellPdf.setHorizontalAlignment(Element.ALIGN_CENTER);
                break;
            case JUSTIFY:
            case FILL:
                cellPdf.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
                break;
            case RIGHT:
                cellPdf.setHorizontalAlignment(Element.ALIGN_RIGHT);
                break;
        }
    }

    private com.itextpdf.text.Font getCellStyle(Cell cell) throws DocumentException, IOException {
        if (cell == null) {
            return FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
        }
        com.itextpdf.text.Font font = new com.itextpdf.text.Font();
        CellStyle cellStyle = cell.getCellStyle();
        org.apache.poi.ss.usermodel.Font cellFont = cell.getSheet()
                .getWorkbook()
                .getFontAt(cellStyle.getFontIndexAsInt());

        if (cellFont.getItalic()) {
            font.setStyle(Font.ITALIC);
        }

        if (cellFont.getStrikeout()) {
            font.setStyle(Font.STRIKETHRU);
        }

        if (cellFont.getUnderline() == 1) {
            font.setStyle(Font.UNDERLINE);
        }

        short fontSize = cellFont.getFontHeightInPoints();
        font.setSize(fontSize);

        if (cellFont.getBold()) {
            font.setStyle(Font.BOLD);
        }

        String fontName = cellFont.getFontName();
        if (FontFactory.isRegistered(fontName)) {
            font.setFamily(fontName);
        } else {
            /*System.out.println("Unsupported font type: {}" + fontName);*/
            /*logger.warn("Unsupported font type: {}", fontName);*/
            font.setFamily("Helvetica");
        }

        return font;
    }

    private void setBackgroundColor(Cell cell, PdfPCell cellPdf) {
        if (cell == null) {
            return;
        }

        short bgColorIndex = cell.getCellStyle()
                .getFillForegroundColor();
        if (bgColorIndex != IndexedColors.AUTOMATIC.getIndex()) {
            XSSFColor bgColor = (XSSFColor) cell.getCellStyle()
                    .getFillForegroundColorColor();
            if (bgColor != null) {
                byte[] rgb = bgColor.getRGB();
                if (rgb != null && rgb.length == 3) {
                    cellPdf.setBackgroundColor(new BaseColor(rgb[0] & 0xFF, rgb[1] & 0xFF, rgb[2] & 0xFF));
                }
            }
        }
    }

    // Utility method to get cell value as string
    private static String getCellValue(Cell cell, FormulaEvaluator evaluator) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                CellValue cellValue = evaluator.evaluate(cell);
                return switch (cellValue.getCellType()) {
                    case STRING -> cellValue.getStringValue();
                    case NUMERIC -> String.valueOf(cellValue.getNumberValue());
                    case BOOLEAN -> String.valueOf(cellValue.getBooleanValue());
                    default -> "";
                };
            case BLANK:
                return " ";
            default:
                return " ";
        }
    }
}
