package Engine.QueryBuilder;

import Engine.Parser.QueryParser;
import Engine.Query.JoinClause;
import Engine.Query.QueryClause;
import Engine.Query.WhereClause;
import java.util.*;

public class AbstractQueryBuilder {
    QueryParser queryParser=new QueryParser();
    Scanner r =new Scanner(System.in);
    public void buildWhere(QueryClause q){
        System.out.println("Enter the where conditon else enter nil");
        String condition=r.nextLine();
        if(!condition.equals("nil")){
            WhereClause where=queryParser.parseQuery(condition);
            q.where(where);
            return;
        }
        q.where(null);
    }
    public void buildJoin(QueryClause q){
        System.out.println("Do you want to add join condition (yes/no)");
        String choice = r.nextLine();
    
        while(choice.equalsIgnoreCase("yes")){
            System.out.println("Enter Table Name and condition to join in csv format");
            String s1=r.nextLine();
            String s2[]=s1.split("\\s*,\\s*");
            String condition[]=s2[1].split("\\s*=\\s*");
            q.join(new JoinClause(s2[0], condition[0], condition[1]));
            System.out.println("Do you want to add another join condition (yes/no)");
            choice = r.nextLine();
        }
    }

}
