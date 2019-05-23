package jExcel;

/**
 *
 * @author Angel
 */
public class Text{
    private String value="";
    public Text(String value){
        this.value=value;
    }
    public void setValue(String value){
        this.value=value;
    }
    public String getValue(){
        return this.value;
    }
    public void echo(){
        System.out.println(value);
    }
}
