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
            if(part.matches("(?i)(sum|avg|min|max|cnt)\\s*\\[.*\\]")){
                String func=part.substring(0,part.indexOf('[')).toUpperCase();
                String column=part.substring(part.indexOf('[')+1,part.indexOf(']')).trim();
                q.aggregate(new Aggregate(func, column));
            }
            else{
                cols.add(part);
            }
        }
        q.select(cols);
    }
}
