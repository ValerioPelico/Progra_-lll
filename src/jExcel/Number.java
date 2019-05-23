package jExcel;

/**
 *
 * @author Angel
 */
public class Number extends General{
    protected int decimalPlaces=0;
    public Number(double value){
        super(value);
    }
    public void echo(){
        System.out.println(String.format( "%."+String.valueOf(decimalPlaces)+"f", this.getValue() ));
    }

    public void setDecimalPlaces(int i){
        this.decimalPlaces=i;
    }


}
