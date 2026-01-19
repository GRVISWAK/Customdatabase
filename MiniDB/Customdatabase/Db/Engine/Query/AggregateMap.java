package Engine.Query;

import java.util.*;

public class AggregateMap{
    public LinkedHashMap<String,Long> sum=new LinkedHashMap<>();
    public LinkedHashMap<String,Long> avg=new LinkedHashMap<>();
    public LinkedHashMap<String,Long> cnt=new LinkedHashMap<>();
    public LinkedHashMap<String,Long> min=new LinkedHashMap<>();
    public LinkedHashMap<String,Long> max=new LinkedHashMap<>(); 
}