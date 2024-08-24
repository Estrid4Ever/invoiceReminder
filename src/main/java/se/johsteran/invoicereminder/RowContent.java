package se.johsteran.invoicereminder;

import java.util.ArrayList;

public class RowContent {
    private ArrayList<String> cellContents;

    public RowContent() {
        this.cellContents = new ArrayList<>();
    }

    public void addCell(String cell) {
        cellContents.add(cell);
    }

    public ArrayList<String> getCellContents() {
        return cellContents;
    }

    @Override
    public String toString() {
        return "RowContent{" +
                "cellContents=" + cellContents +
                '}';
    }

    public void setCellContents(ArrayList<String> cellContents) {
        this.cellContents = cellContents;
    }
}
