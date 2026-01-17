package Engine.QueryBuilder;

import java.util.*;

import Engine.Parser.QueryParser;
import Engine.Query.QueryClause;
import Engine.Query.QueryType;

public class ConsoleQueryBuilder {
    SelectQueryBuilder selectBuilder=new SelectQueryBuilder();
    DeleteQueryBuilder deleteBuilder=new DeleteQueryBuilder();
    QueryParser queryParser=new QueryParser();
    Scanner r=new Scanner(System.in);
    public QueryClause build(QueryType type) throws Exception{
        switch(type){
            case SELECT:{
                return selectBuilder.buildSelect();
            }
        }
        switch(type){
            case DELETE:{
                return deleteBuilder.buildDelete();
            }
        }
        throw new Exception("Unknown query type");
    }
}