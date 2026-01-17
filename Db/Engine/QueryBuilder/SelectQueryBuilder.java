package Engine.QueryBuilder;

import Engine.Query.QueryClause;
import Engine.Query.QueryType;

public class SelectQueryBuilder extends AbstractQueryBuilder{
    public QueryClause buildSelect(){
        System.out.println("Enter table name");
        String tableName=r.nextLine();
        QueryClause q=new QueryClause(QueryType.SELECT, tableName);
        System.out.println("Enter the columns to be selected in csv format (to select all columns wnter *) ");
        String columns=r.nextLine();
        q.select(columns.split("\\,"));
        buildJoin(q);
        buildWhere(q);
        return q;
    }
}
