package Metadata;
import java.util.*;

public class ColumnMetadata{
    public String name;
    public String type;
    public boolean pk;

    public ColumnMetadata(String name, String type, boolean pk){
        this.name=name;
        this.type=type;
        this.pk=pk;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public boolean isPrimarykey(){
        return pk;
    }
}