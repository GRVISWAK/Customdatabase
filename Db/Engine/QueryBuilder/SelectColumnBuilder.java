package Engine.QueryBuilder;
import java.util.*;

import Engine.Query.Aggregate;
import Engine.Query.QueryClause;

public class SelectColumnBuilder {
    Scanner r=new Scanner(System.in);
    public void buildSelectColumns(QueryClause q) {
        System.err.println("Enter the select column query");
        String str=r.nextLine();
        String parts[]=str.split("\\s*,\\s*");
        ArrayList<String> cols=new ArrayList<>();
        for(String part:parts){
            part=part.trim();
            if(isAggregate(part)){
                String func=part.substring(0,part.indexOf('[')).toUpperCase();
                String column=part.substring(part.indexOf('['),part.indexOf(']')+1).trim();
                q.aggregate(new Aggregate(func, func+column));
                cols.add(func+column);
            }
            else{
                cols.add(part);
            }
        }
        q.select(cols);
    }
    public boolean isAggregate(String part){
        if(part.matches("(?i)(sum|avg|min|max|cnt)\\s*\\[.*\\]")) return true;
        return false;
    }
}