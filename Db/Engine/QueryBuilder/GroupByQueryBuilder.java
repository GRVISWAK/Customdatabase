package Engine.QueryBuilder;
import java.util.*;

import Engine.Parser.QueryParser;
import Engine.Query.Aggregate;
import Engine.Query.Condition;
import Engine.Query.QueryClause;
import Engine.Query.WhereClause;

public class GroupByQueryBuilder {
    Scanner r=new Scanner(System.in);
    QueryParser queryParser=new QueryParser();
    SelectColumnBuilder columnBuilder=new SelectColumnBuilder();
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
        System.out.println("Do you want to add Having? (yes/no)");
        String havingChoice=r.nextLine();
        if(havingChoice.equals("yes")){
            System.out.println("Enter the having conditon else enter nil");
            String userCondition=r.nextLine();
            if(!userCondition.equals("nil")){
                WhereClause having=queryParser.parseQuery(userCondition);
                for(Condition cond:having.conditions){
                    String part=cond.getCondtionCol();
                    System.out.println(part);
                    if(columnBuilder.isAggregate(part)){
                        String func=part.substring(0,part.indexOf('[')).toUpperCase();
                        String column=part.substring(part.indexOf('['),part.indexOf(']')+1).trim();
                        System.out.println(func+" "+column);
                        if(!q.isAggPresent(new Aggregate(func, func+column)))
                        q.aggregate(new Aggregate(func, func+column));
                    }
                }
                q.having(having);
                return;
            }
            q.having(null);
        }
    }
}