package Engine.QueryBuilder;
import java.util.*;

import Engine.Query.QueryClause;

public class GroupyQueryBuilder {
    Scanner r=new Scanner(System.in);
    public void buildGroupBy(QueryClause q){
        System.out.println("Do you want to add GROUP BY clause? (yes/no)");
        String response=r.nextLine();
        if(response.equalsIgnoreCase("yes")){
            System.out.println("Enter columns to GROUP BY (comma separated):");
            String groupByInput=r.nextLine();
            String[] groupByColumns=groupByInput.split(",");
            for(String col:groupByColumns){
                q.groupByColumns.add(col.trim());
            }
        }
    }
}
