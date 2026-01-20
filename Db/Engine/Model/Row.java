package Engine.Model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;

public class Row {
    LinkedHashMap<String,String> values;


    public Row() {
        this.values = new LinkedHashMap<>();
    }
    public void put(String column, String value) {
        values.putIfAbsent(column, value);
    }

    public String get(String column) {
        return values.get(column);
    }

    public Set<String> getColumns() {
        return values.keySet();
    }

    public Collection<String> values() {
        return values.values();
    }
    public LinkedHashMap<String,String> getValues(){
        return values;
    }
}
