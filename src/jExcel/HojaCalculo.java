package jExcel;
//importamos todas las librerias
//javax swing es para los componentes graficos
import javax.swing.*;
//io.file es para manejar la clase io
import java.io.FileNotFoundException;
//xml.stream es para poder manejar los archovos xml
import javax.xml.stream.XMLStreamException;
/**
 * @author Angel
 */

//menu principal
public class HojaCalculo{
    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        // Clases de prueba
        // La hoja de cálculo es la siguiente
        /**
        *   Name        EPF No  Duración del trabajo     Salario
        *   Monica     1250        1:30:00             Rs.200.00
        *   Juan       1251        3:45:00             Rs.550.00
        *   Felix      1252        0:30:00             Rs.55.50
        *   Pedro      1253        2:30:00             Rs.400.00
        *   Total       -          Tiempo Total         Coste total
        */  
        
        //Formato de celdas y tipos de datos.  *******************************
        Text[] Names= new Text[5];          //Columna de nombres
        Number[] EPF= new Number[5];        //EPF Sin columna
        Time[] WorkDuration= new Time[5];   //Columna de duración del trabajo
        Currency[] Salary= new Currency[5]; //Columna de salario
        
        for(int n = 0; n < 5; n ++){
            Salary[n] = new Currency(0.0);
            Salary[n].setSymbol("Rs.");
            Salary[n].setDecimalPlaces(2);
        }
        
        //almacenamiento de datos *************************************************
        Names[0]=new Text("Amal");
        Names[1]=new Text("Kamal");
        Names[2]=new Text("Lahiru");
        Names[3]=new Text("Chamara");
        
        for(int n = 0; n < 4; n ++){
            EPF[n] = new Number(1250+n);
        }
        
        WorkDuration[0]=new Time(1,30,0.0);
        WorkDuration[1]=new Time(3,45,0.0);
        WorkDuration[2]=new Time(0,30,0.0);
        WorkDuration[3]=new Time(2,30,0.0);

        Salary[0].setValue(200.00);
        Salary[1].setValue(550.00);
        Salary[2].setValue(55.50);
        Salary[3].setValue(400.00);
        
        //generando fila total ***************************************
        Names[4]=new Text("Total");
        EPF[4] = new Number(0);

        WorkDuration[4]=new Time(0,0,0.0);
        WorkDuration[4].add(WorkDuration[0], WorkDuration[1]);
        WorkDuration[4].add(WorkDuration[4], WorkDuration[2]);
        WorkDuration[4].add(WorkDuration[4], WorkDuration[3]);
        
        Salary[4].add(Salary[0].getValue(), Salary[1].getValue(), Salary[2].getValue(), Salary[3].getValue());

        System.out.print("Duración total del trabajo H:M:S\t:");WorkDuration[4].echo();
        System.out.print("Coste total\t\t\t:");Salary[4].echo();

        System.out.println("tipo de datos del resultado de 10+15.0 es un "+resultdType.resultdTypeStr("10+15.1"));
        System.out.println("tipo de datos del resultado de lahiru+2014/99/99 es un "+resultdType.resultdTypeStr("lahiru+2014/99/99"));
        System.out.println("tipo de datos del resultado de LKR10.00-$1 es un "+resultdType.resultdTypeStr("LKR10.00+$1"));
        System.out.println("tipo de datos del resultado de 2014/01/01+2014/12/01 es un "+resultdType.resultdTypeStr("2014/01/01+2014/12/01"));

        String expression1="(5+2*3)";
        String expression2="Lahiru+Jayakody";
        InfixExpression a=new InfixExpression(expression1);
        System.out.println(""+a.convertToPostfix());
        resultValue answer1 = new resultValue(expression1);
        System.out.println("Respuesta para "+expression1+" es "+answer1.getResultStr());
        
        resultValue answer2 = new resultValue(expression2);
        System.out.println("Respuesta para "+expression2+" es "+answer2.getResultStr()); 
        
        System.out.println(Celda.colonRemove("=sum(F0:E0)*sum(A0:E0)"));
       
       //Iniciar jExcel GUI

       Window instance=new Window(null);
       instance.getBook().addNewSheet();        //agrega una hoja por defecto
       SwingUtilities.invokeLater(instance);
    }

}
