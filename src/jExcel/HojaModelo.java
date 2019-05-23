package jExcel;
//importamos la libreria javax swing
import javax.swing.table.*;

//creamos la clase de hoja y la extendemos de el modelo abstracto de la tabla
class HojaModelo extends AbstractTableModel {
    //conemos 3 variables encapsuladas
    final private Sheet guiTable;
    private int guiRow;
    private int guiColumn;

    protected Celda[][] cells;
    //constructor del metodo
    HojaModelo(Celda[][] cells, Sheet table) {
        guiTable = table;
        guiRow = cells.length;
        guiColumn = cells[0].length;
        this.cells = cells;
    }
    //creamos el getter de la barra de formulas
    public String getFormularBarText(int r, int c) {
        String s = "Haga clic en una celda";
        if ((r + 1) * (c + 1) > 0) {
            if (cells[r][c].getFormula().length() < 1) {
                s = cells[r][c].getValue();
            } else {
                s = "=" + cells[r][c].getFormula();
            }
            guiTable.getBook().getContainer().getFormulaBar().setEditable(true);
        }else{
        guiTable.getBook().getContainer().getFormulaBar().setEditable(false);
        }
        return s;
    }
    //getter de la cuenta de las filas
    public int getRowCount() {
        return guiRow;
    }
    //getter de la cuenta de las columnas
    public int getColumnCount() {
        return guiColumn;
    }
    //metodo booleano que nos indica si la celda es editable
    public boolean isCellEditable(int row, int col) {
        return true;
    }
    //setter del la variable tipo objeto
    public Object getValueAt(int row, int column) {
        //if(cells[row][column].rawvalued==false){cells[row][column].Compute(cells[row][column].formula, cells[row][column].sheet.book);}
        String r = cells[row][column].getValue();
        cells[row][column].setEditing(false);
        return r;

    }
    //setter de la variable tipo objeto
    public void setValueAt(Object value, int row, int column) {
        String input = (String) value;
        if (input.equals("=")) {
            return;
        }
        guiTable.getBook().setSaved(false);
        if (value != null) {
            cells[row][column].Compute(input, guiTable.getBook());
        } else {
            cells[row][column].setValueObject("");
            cells[row][column].setFormula("");
        }
        guiTable.repaint();
    }

}
