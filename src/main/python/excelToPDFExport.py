import sys
import win32com.client

def excel_to_pdf(excel_path, pdf_path):
    excel = win32com.client.Dispatch("Excel.Application")
    excel.Visible = False

    # Open the Excel file
    workbook = excel.Workbooks.Open(excel_path)

    # Export as PDF
    workbook.ExportAsFixedFormat(0, pdf_path)  # 0 = xlTypePDF

    # Close the workbook and Excel
    workbook.Close(False)
    excel.Quit()

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python excel_to_pdf.py <excel_path> <pdf_path>")
        sys.exit(1)

    excel_path = sys.argv[1]
    pdf_path = sys.argv[2]

    excel_to_pdf(excel_path, pdf_path)
