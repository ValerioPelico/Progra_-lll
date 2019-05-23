package jExcel;

/**
 *
 * @author Angel
 */
//creamos una clase public que se extiende de numero
public class Currency extends Number {
    //declaramos la variable String y le ponemos el simbolo dolar
    private String currencySymbol="$";
    //constructor de tipo doble
    public Currency(double value){
        super(value);
    }
    //creamos un void para que nos muestre en pantalla un conjuto de palabras y numeros
    public void echo(){
        System.out.println(String.format( currencySymbol+" %."+String.valueOf(decimalPlaces)+"f",this.getValue() ));
    }
    //setter del simbolo
    public void setSymbol(String symbol){
        this.currencySymbol=symbol;
    }
    //getter del simbolo
    public String getSymbol(){
        return this.currencySymbol;
    }
}
