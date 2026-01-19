package Engine.QueryBuilder;

import Engine.Query.QueryClause;
import Engine.Query.QueryType;
import java.util.*;

public class DeleteQueryBuilder extends AbstractQueryBuilder{
    Scanner r=new Scanner(System.in);
    public QueryClause buildDelete(){
        System.out.println("Enter table name");
        String tableName=r.nextLine();
        QueryClause q=new QueryClause(QueryType.DELETE, tableName);
        buildWhere(q);
        return q;
    }
}
