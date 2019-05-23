package jExcel;

/**
 *
 * @author Angel
 */

//creamos una clase de tipo abstracta llamada general
abstract public class General{
    private double value=0.0;
    public General(double value){
        this.value=value;
    }
    //aqui mostramos con un echo
    abstract public void echo();
    //setters u getters
    public void setValue(double value){
        this.value=value;
    }
    public double getValue(){
        return this.value;
    }
    //tenemos un public void add para agregar valores al cualquier argumento
        public void add(double... values){
         double temp=0.0;
         for (Double arg : values) {temp+=arg;}
         setValue(temp);
    }
        /*
        ya aqui creamos 5 
        metodos para las operaciones matematicas
        los cuales son resta division, multiplicacion el maximo y minimo
        */
    public void substract(double value1,double value2){
         setValue(value1-value2);
    }
    public void divide(double value1,double value2){
         setValue(value1/value2);
    }
    public void multiply(double value1,double value2){
         setValue(value1*value2);
    }
    //vemos cual de las variables es mas grande
    public void max(double value1,double value2){
        if(value1>=value2){
            setValue(value1);
        }
        else{
            setValue(value2);;
        }
    }
    //vemos el numero menor
    public void min(double value1,double value2){
        if(value1<value2){
            setValue(value1);
        }
        else{
            setValue(value2);;
        }
    }
}
