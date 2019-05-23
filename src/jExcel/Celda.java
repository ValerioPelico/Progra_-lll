package jExcel;
//importamos la libreria que nos ayuda a manejar listas en nuestro programa
import java.util.ArrayList;

/**
 *
 * @author Angel
 */
//creamos una clase llamada celda
class Celda {
    //encapsulamos cada una de las variables a utilizar
    //la variable object significa que acepta cualquier tipo de valor que se ingrese(int,string,etc)
    private Object Object_value;
    private String String_formula;
    private String String_rformula;
    int state;
    /*
    creamos las listas que se utilizan en cada celda, y el listener
    es el metodo que ve cuando uno selecciona una celda
    */
    private ArrayList<Celda> lista_listeners;
    /*
    creamos otra variable de tipo lista a la que le llamamos references, y
    nos ayuda a saber en que celda se ubica el contenido escrito
    */
    private ArrayList<Celda> lista_references;
    //variable int para las filas
    private int int_fila;
    private int int_columna;
    //variable booleana que nos indica si una celda ha sido modificada
    private boolean bool_editing;
    //creamos una matrix donde iran los datos
    private Celda[][] matrix_celdas;
    //creamos una variable de tipo sheet(hoja) 
    private Sheet sheet_hoja;
    private boolean rawvalued;
    /*
    creamos el constructor de las clases
    poniendo como parametros las filas,columnas y la variable sheet
    */
    Celda(Sheet sheet, int int_fila, int int_columna) {
        //igualamos variables
        int_fila = int_fila;
        int_columna = int_columna;
        //comenzamos a igualar las variables en blanco
        Object_value = "";
        String_formula = "";
        String_rformula = "";
        lista_listeners = new ArrayList<>();
        bool_editing = false;
        rawvalued = false;
        this.sheet_hoja = sheet;
    }//fin del constructor

    //constructor de la CELDA
    Celda(Sheet sheet, int int_row, int int_col, String String_value, String String_formula) {
        //a esta variable le pasamos los valores que requiere
        this(sheet, int_row, int_col);
        this.Object_value = String_value;
        this.String_formula = String_formula;
    }//fin del segundo constructor

    //creamos el getter de value
    public String getValue() {
        
        /*
        creamos un if verificando si el tamaño de la formula es menor a 1 o si no
        se ha editado nada, entonces valuamos el objeto y lo convertimos a string
        */
        if (getFormula().length() < 1 || !isEditing()) {
            String strV = getValueObject().toString();
        /*
            luego de eso vemos si tiene alguno de esos simbolos
            */
            if (strV.matches("[-+]?([0-9]+\\.[0])")) {
                //elimina los ceros no deseados después del punto decimal.
                strV = strV.split("\\.")[0];             
            }
            //if que ve si la formula es igual a la otra formula y convierte en mayusula
            if (strV.equals(String_formula.toUpperCase())) {
                strV = String_formula;                             
            }
            return strV;
        } else {
            //de lo contrario envia falso, que no ha sido editada
            setEditing(false);
            return "=" + getFormula();
        }

    }
    //creamos un metodo void compute para que 
    public void Compute(String String_input, Libro Libro_libro) {
        //obtenemos del libro que creamos la hoja y el modelo de las celdas
        setCells(Libro_libro.getSheets().get(0).getSheetModel().cells);
        //ya creamos una nueva lista
        setReferences(new ArrayList<Celda>());
        //creamos un if donde vemos si todo lo ingresado es menor a 1(nulo)
        if (String_input.length() < 1) {
            //dejamos los setters de formula y objeto en blanco
            this.setFormula("");
            this.setValueObject("");
            //vemos si el string ingresado, el primer caracter ingresado no es igual a "="
        } else if (String_input.charAt(0) != '=') {
            //dejamos la variable formula en blanco
            this.setFormula("");
            //y capturamos la variable como un tipo objeto
            this.setValueObject(String_input);
        } else {
            //de lo contrario si lo que ingresamos contiene un igual
            String_input = String_input.split("=")[1];
            //creamos una formula
            this.setFormula(String_input);
            //aqui cambiamos el separador medio por la rayita de la ñ
            String tf = this.getFormula().replaceAll("\\[\\-", "\\[\\~");
            for (String x : tf.split("[\\+\\*\\-\\)\\(/,:\\!]")) {
                x = x.replaceAll("\\[\\~", "\\[\\-");
                if (x.matches("R(\\[[0-9\\-]+\\])*C(\\[[0-9\\-]+\\])*")) {
                    int r, c;
                    String tcol = x.replaceAll("R(\\[[0-9\\-]+\\])*", "");
                    String trow = x.replaceAll("C(\\[[0-9\\-]+\\])*", "");
                    if (tcol.matches("C")) {
                        c = 0;
                    } else {
                        c = Integer.parseInt(tcol.replaceAll("(C\\[)|(\\])", ""));
                    }
                    if (trow.matches("R")) {
                        r = 0;
                    } else {
                        r = Integer.parseInt(trow.replaceAll("(R\\[)|(\\])", ""));
                    }
                    c += getColumn();
                    r += getRow();
                    String s = (c / 26 < 1 ? "" : Character.valueOf((char) (c / 26 + 64)).toString()) + Character.valueOf((char) (c % 26 + 65)).toString() + (r + 1);
                    setFormula(getFormula().replace(x, s));
                    String_input = getFormula();
                }
            }

            //Capitalizador
            for (String x : String_input.split("[\\+\\*\\-\\)\\(/,:\\!]")) {
                if (x.matches("[a-z]{1,2}[1-9][0-9]*") | x.matches("[A-Za-z0-9]+")) {
                    String_input = String_input.replaceAll(x, x.toUpperCase());
                }
            }

            //Hacer formula relativa
            this.setRformula(String_input);
            for (String x : getRformula().split("[\\+\\*\\-\\)\\(/,:\\!]")) {
                if (x.matches("[A-Z]{1,2}[1-9][0-9]*")) {
                    String col = x.replaceAll("[0-9]", "");
                    if (col.length() == 1) {
                        col = "@".concat(col);
                    }
                    int tmp = col.charAt(1) - 64;
                    int c = 26 * (col.charAt(0) - 64) + tmp;
                    int r = Integer.parseInt(x.replaceAll("[A-Z]", ""));
                    String s = "";
                    if (r - 1 - getRow() == 0) {
                        s += "R";
                    } else {
                        s += "R[" + (r - 1 - getRow()) + "]";
                    }
                    if (c - 1 - getColumn() == 0) {
                        s += "C";
                    } else {
                        s += "C[" + (c - 1 - getColumn()) + "]";
                    }
                    setRformula(getRformula().replaceFirst(x, s));
                }
            }
            //: Simplificador
            if (String_input.contains(":")) {
                String_input = Celda.colonRemove(String_input);
            }
            //sustituto
            for (String x : String_input.split("[\\+\\*\\-\\)\\(/,]")) {
                String tmpx = x;
                String orinput = String_input;
                if (x.matches("[A-Z]{1,2}[1-9][0-9]*") | x.matches("[A-Za-z0-9]+![A-Z]{1,2}[1-9][0-9]*")) {
                    if (x.matches("[A-Za-z0-9]+![A-Z]{1,2}[1-9][0-9]*")) {
                        int i = Libro_libro.getSheetCollection().indexOfTab(x.split("!")[0]);
                        setCells(Libro_libro.getSheets().get(i).getSheetModel().cells);
                        tmpx = x.split("!")[1];
                    } else {
                        setCells(getSheet().getSheetModel().cells);
                    }
                    String col = tmpx.replaceAll("[0-9]", "");
                    if (col.length() == 1) {
                        col = "@".concat(col);
                    }
                    int tmp = col.charAt(1) - 64;
                    int c = 26 * (col.charAt(0) - 64) + tmp;
                    int r = Integer.parseInt(tmpx.replaceAll("[A-Z]", ""));
                    if(r>getCells().length || c>getCells()[0].length){
                        setValueObject("#ERROR");
                        return;
                    }
                    if (getCells()[r - 1][c - 1].equals(this)) {
                        this.setValueObject("0");
                        System.out.println("La misma celda no puede ser referida");
                        return;
                    }
                    if (!matrix_celdas[r - 1][c - 1].lista_listeners.contains(this)) {
                        getCells()[r - 1][c - 1].getListeners().add(this);
                    }
                    String s = getCells()[r - 1][c - 1].getValue();
                    if (s.length() < 1) {
                        s = "0";
                    }
                    String_input = String_input.replaceFirst(x, s);
                    this.getReferences().add(getCells()[r - 1][c - 1]);
                }
            }
            if (String_input.matches("([A-Z]+|[a-z]+)\\([0-9][0-9,-\\.]+[\\)]")) {
                String_input = resultValue.getRawExpression(String_input);
            }
            try {
                resultValue result = new resultValue(String_input);
                this.setValueObject(result.getResultStr());
            } catch (Exception e) {
                this.setValueObject("#ERROR");
            }
        }
            //actualización

        for (Celda x : new ArrayList<>(getListeners())) {

            if (x.getReferences().contains(this)) {
                x.Compute("=" + x.getFormula(), Libro_libro);
            } else {
                this.getListeners().remove(x);
            }
        }
        this.setRawvalued(false);
    }

    public static String colonRemove(String input) {
        for (String x : input.split("[\\+\\*\\-\\)\\(/]")) {
            if (!x.contains(":")) {
                continue;
            }
            String l = x.split(":")[0];
            String r = x.split(":")[1];
            String l0 = l.replaceAll("[0-9]*", "");
            int l1 = Integer.parseInt(l.replaceAll("[A-Z]*", ""));
            String r0 = r.replaceAll("[0-9]*", "");
            int r1 = Integer.parseInt(r.replaceAll("[A-Z]*", ""));
            if (l0.equals(r0)) {
                if ((l1 - r1) * (l1 - r1) == 1) {
                    input = input.replaceAll(x, l + "," + r);
                } else {
                    if (l1 > r1) {
                        int t = l1;
                        l1 = r1;
                        r1 = t;
                    }
                    String midString = ",";
                    for (Integer i = l1 + 1; i < r1; i++) {
                        midString += l0 + i.toString() + ",";
                    }
                    input = input.replaceAll(x, l + midString + r);
                }
            } else if (l1 == r1) {
                Character cl0 = l0.charAt(0);
                Character cr0 = r0.charAt(0);
                if (Math.abs(cl0 - cr0) == 1) {
                    input = input.replaceAll(x, l + "," + r);
                } else {
                    if (cl0 > cr0) {
                        Character t = cl0;
                        cl0 = cr0;
                        cr0 = t;
                    }
                    String midString = ",";
                    for (Character i = ++cl0; i < cr0; i++) {
                        midString += i.toString() + l1 + ",";
                    }
                    input = input.replaceAll(x, l + midString + r);
                }
            }
        }

        return input;
    }

    /**
     * retornamos la formula con el getter
     */
    public String getFormula() {
        return String_formula;
    }

    /**
     * @param formula aqui declaramos la formula
     */
    public void setFormula(String formula) {
        this.String_formula = formula;
    }

    /**
     * @return the rformula
     */
    public String getRformula() {
        return String_rformula;
    }

    /**
     * @param rformula declaramos la respuesta de la formula
     */
    public void setRformula(String rformula) {
        this.String_rformula = rformula;
    }

    /**
     * retornamos la lista con los getters correspondientes
     */
    public ArrayList<Celda> getListeners() {
        return lista_listeners;
    }

    /**
     * aqui declaramos lo que ya tenemos en la celda con la ayuda de 
     * la lista y el metodo setter la declaramos
     */
    public void setListeners(ArrayList<Celda> listeners) {
        this.lista_listeners = listeners;
    }

    /**
     * retornamos la referencia con el metodo getter
     */
    public ArrayList<Celda> getReferences() {
        return lista_references;
    }

    /**
     *  se guia para saber en que celda esta con la ayuda de este set
     */
    public void setReferences(ArrayList<Celda> references) {
        this.lista_references = references;
    }

    /**
     * getter de las filas
     */
    public int getRow() {
        return int_fila;
    }

    /**
     * setter de las filas
     */
    public void setRow(int row) {
        this.int_fila = row;
    }

    /**
     * getter de las columnas
     */
    public int getColumn() {
        return int_columna;
    }

    /**
     * setter para las columnas
     */
    public void setColumn(int column) {
        this.int_columna = column;
    }

    /**
     * setter para ver si esta editando
     */
    public boolean isEditing() {
        return bool_editing;
    }

    /**
     * setter del editar
     */
    public void setEditing(boolean editing) {
        this.bool_editing = editing;
    }

    /**
     * getter de las celdas
     */
    public Celda[][] getCells() {
        return matrix_celdas;
    }

    /**
     * setter de las celdas
     */
    public void setCells(Celda[][] cells) {
        this.matrix_celdas = cells;
    }

    /**
     * obtenemos el valor de la hoja con el getter
     */
    public Sheet getSheet() {
        return sheet_hoja;
    }

    /**
     * declaramos el valor de la hoja con el metodo setter
     */
    public void setSheet(Sheet sheet) {
        this.sheet_hoja = sheet;
    }

    /**
     *  esta lleva
     */
    public boolean isRawvalued() {
        return rawvalued;
    }

    /**
     * setter de 
     */
    public void setRawvalued(boolean rawvalued) {
        this.rawvalued = rawvalued;
    }

    /**
     * getter del valor object
     */
    public Object getValueObject() {
        return Object_value;
    }

    /**
     * +setter del valor objeto
     */
    public void setValueObject(Object value) {
        this.Object_value = value;
    }
}
