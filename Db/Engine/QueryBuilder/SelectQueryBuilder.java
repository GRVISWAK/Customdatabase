package Engine.QueryBuilder;

import Engine.Query.QueryClause;
import Engine.Query.QueryType;

public class SelectQueryBuilder extends AbstractQueryBuilder{
    SelectColumnBuilder columnBuilder=new SelectColumnBuilder();
    GroupByQueryBuilder groupByBuilder=new GroupByQueryBuilder();
    public QueryClause buildSelect(){
        System.out.println("Enter table name");
        String tableName=r.nextLine();
        QueryClause q=new QueryClause(QueryType.SELECT, tableName);
        columnBuilder.buildSelectColumns(q);
        buildJoin(q);
        buildWhere(q);
        groupByBuilder.buildGroupBy(q);
        return q;
    }
}