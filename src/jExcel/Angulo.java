package jExcel;

/**
 *
 * @author Angel
 */

//clase angulo
public class Angulo {
    //encapsulamos todas las variables que vamos a utilizar
    //este string nos pondra como delimitador el .
    private String String_delimitador=".";
    //esta variable double nos da un limite maximo en la casilla 
    protected int int_limite=360;
    private double double_level0=0.0;
    private int int_level1=0;
    private int int_level2=0;
    /*
    aqui creamos el constructor, declarando las variables
    */
    public Angulo(int v2,int v1,double v0){
        this.double_level0=v0;
        this.int_level1=v1;
        this.int_level2=v2;        
    }
    //creamos el set para poner los valores de cada uno
    public void setValue(int v2,int v1,double v0){
        this.double_level0=v0;
        this.int_level1=v1;
        this.int_level2=v2;
    }
    //aqui creamos el setter del simbolo que se utilice
    public void setSymbol(String symbol){
        this.String_delimitador=symbol;
    }
    //aqui creamos un void que nos mostrara en pantalla todo lo que hemos escrito en el archovo de excel
    public void echo(){
        System.out.println(int_level2+String_delimitador+int_level1+String_delimitador+double_level0);
    }
    //creamos el getter del value anterior, metiendolo todo en un vector
    public double[] getValue(){
        double[] value={this.double_level0,this.int_level1,this.int_level2};
        return value;
    }
    
    public void add(Angulo angle1,Angulo angle2){
        double[] value1=angle1.getValue();
        double[] value2=angle2.getValue();
        double temp1=0.0;
        double temp2=0.0;
        for(int i=0;i<3;i++){
            temp1+=value1[i]*Math.pow(int_limite, i);
        }
        for(int i=0;i<3;i++){
            temp2+=value2[i]*Math.pow(int_limite, i);
        }
        temp1+=temp2;
        
        this.double_level0=temp1%int_limite;temp1=temp1/int_limite;
        this.int_level1=(int)(temp1%int_limite);temp1=temp1/int_limite;
        this.int_level2=(int)(temp1);
    }
    public void substract(Angulo angle1,Angulo angle2){
        double[] value1=angle1.getValue();
        double[] value2=angle2.getValue();
        this.double_level0=value1[0]+value1[0];
        this.int_level1=(int)(value1[0]+value1[0]);
        this.int_level2=(int)(value1[0]+value1[0]);
    }
}
