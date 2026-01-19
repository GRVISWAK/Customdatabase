package Engine.Query;
import java.util.*;

public class GroupKey {
    public LinkedHashMap<String,String> key=new LinkedHashMap<>();
    public void addGroupKey(String column,String value){
        key.put(column,value);
    }
}
