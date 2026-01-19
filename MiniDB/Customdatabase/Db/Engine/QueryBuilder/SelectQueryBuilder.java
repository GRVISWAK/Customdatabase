package Engine.QueryBuilder;

import Engine.Query.QueryClause;
import Engine.Query.QueryType;

public class SelectQueryBuilder extends AbstractQueryBuilder{
    SelectColumnBuilder columnBuilder=new SelectColumnBuilder();
    GroupyQueryBuilder groupByBuilder=new GroupyQueryBuilder();
    public QueryClause buildSelect(){
        System.out.println("Enter table name");
        String tableName=r.nextLine();
        QueryClause q=new QueryClause(QueryType.SELECT, tableName);
        columnBuilder.buildSelectColumns(q);
        buildJoin(q);
        groupByBuilder.buildGroupBy(q);
        buildWhere(q);
        return q;
    }
}
