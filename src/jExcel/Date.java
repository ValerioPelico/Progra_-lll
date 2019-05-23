package jExcel;

/**
 *
 * @author Angel
 */
//en esta clase declararemos la fecha
public class Date{
    int int_año=1000;
    int int_mes=1;
    int int_dia=1;
    //colocamos en pantalla
    public void echo(){
        System.out.println(String.format(String.valueOf(int_año)+"/"+String.valueOf(int_mes)+"/"+ String.valueOf(int_dia)));
    }
}
