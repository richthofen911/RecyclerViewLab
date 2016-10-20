package callofdroidy.net.recyclerviewlab;

/**
 * Created by yli on 07/10/16.
 */

public class Cell {
    private int name;
    private int value;

    public Cell(int name, int value){
        this.name = name;
        this.value = value;
    }

    public void setName(int name){
        this.name = name;
    }

    public int getName(){
        return name;
    }

    public void setValue(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
