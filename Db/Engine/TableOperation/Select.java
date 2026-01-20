package Engine.TableOperation;
import java.util.*;

import Engine.Model.Row;
import Engine.Parser.LogicalExpression;
import Engine.Query.Condition;
import Engine.Query.JoinClause;
import Engine.Query.QueryClause;
import Engine.Query.WhereClause;

import java.io.File;

public class Select {
    LogicalExpression logicalExpression=new LogicalExpression();
    Condition condition;
    WhereEvaluator whereEvaluator=new WhereEvaluator();
    public void selectData(File currDbDir,QueryClause query) throws Exception{
        String tableName=query.getTable();
        ArrayList<String> selectCol=query.getSelectedColumn();
        ArrayList<JoinClause> joins=query.getJoins();
        WhereClause whereClause=query.getWhereClause();
        GroupBy groupBy=new GroupBy();
        TableScanner tableScanner=new TableScanner();
        JoinExecutor joinExecutor=new JoinExecutor();
        ArrayList<Row> rows=tableScanner.scanTable(currDbDir,tableName);
        //apply joins
        for(JoinClause join:joins){
            rows=joinExecutor.applyJoin(currDbDir, join, rows);
        }
        //apply where
        if(whereClause!=null) rows=whereEvaluator.applyWhere(rows, whereClause);

        //apply group by
        if(!query.getGroupByColumns().isEmpty()){
            rows=groupBy.applyGroupBy(rows,query);
        }
        //apply having
        if(query.havingClause!=null) rows=whereEvaluator.applyWhere(rows, query.havingClause);

        //get final table column list
        if(selectCol.get(0).equals("*")){
            selectCol.remove("*");
            for(String column:rows.get(0).getColumns()){
                selectCol.add(column);
            }
        }
        for(String str:selectCol){
            System.out.print(str+"|");
        }
        System.out.println();
        for(Row row:rows){
            for(String column:selectCol){
            for(Map.Entry<String,String> entry:row.getValues().entrySet()){
                if(column.equals(entry.getKey())){
                    System.out.print(entry.getValue()+"|");
                }
            }
            }
            System.out.println();
        }
    }
}